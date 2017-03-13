package com.jianglibo.nutchbuilder.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class NameValueconfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(NameValueconfiguration.class);

	static final String UNKNOWN_RESOURCE = "Unknown";

	private Document parse(DocumentBuilder builder, String content) throws IOException, SAXException {
		if (content == null) {
			return null;
		}

		InputStream is = new ByteArrayInputStream(content.getBytes());
		try {
			return builder.parse(content);
		} finally {
			is.close();
		}
	}

	private ClassLoader classLoader;
	{
		classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = NameValueconfiguration.class.getClassLoader();
		}
	}

	public URL getResource(String name) {
		return classLoader.getResource(name);
	}

	private static class Resource {
		private final Object resource;
		private final String name;

		public Resource(Object resource, String name) {
			this.resource = resource;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Object getResource() {
			return resource;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public Properties loadResource(Properties properties, Resource wrapper) {
		String name = UNKNOWN_RESOURCE;
		try {
			name = wrapper.getName();
			Object rc = wrapper.getResource();
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			// ignore all comments inside the xml file
			docBuilderFactory.setIgnoringComments(true);

			// allow includes in the xml file
			docBuilderFactory.setNamespaceAware(true);
			try {
				docBuilderFactory.setXIncludeAware(true);
			} catch (UnsupportedOperationException e) {
				LOG.error("Failed to set setXIncludeAware(true) for parser " + docBuilderFactory + ":" + e, e);
			}
			DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
			Document doc = null;
			Element root = null;

			if (rc instanceof String) {
				doc = parse(builder, (String) rc);
			} else if (rc instanceof Element) {
				root = (Element) rc;
			}

			if (root == null) {
				if (doc == null) {
					throw new RuntimeException("resource not found");
				}
				root = doc.getDocumentElement();
			}
			Properties toAddTo = properties;

			if (!"configuration".equals(root.getTagName()))
				LOG.error("bad conf file: top-level element not <configuration>");
			NodeList props = root.getChildNodes();
			for (int i = 0; i < props.getLength(); i++) {
				Node propNode = props.item(i);
				if (!(propNode instanceof Element))
					continue;
				Element prop = (Element) propNode;
				if ("configuration".equals(prop.getTagName())) {
					loadResource(toAddTo, new Resource(prop, name));
					continue;
				}
				if (!"property".equals(prop.getTagName()))
					LOG.warn("bad conf file: element not <property>");
				NodeList fields = prop.getChildNodes();
				String attr = null;
				String value = null;
				for (int j = 0; j < fields.getLength(); j++) {
					Node fieldNode = fields.item(j);
					if (!(fieldNode instanceof Element))
						continue;
					Element field = (Element) fieldNode;
					if ("name".equals(field.getTagName()) && field.hasChildNodes())
						attr = ((Text) field.getFirstChild()).getData().trim();
					if ("value".equals(field.getTagName()) && field.hasChildNodes())
						value = ((Text) field.getFirstChild()).getData();
				}
				toAddTo.setProperty(attr, value);
			}
			return toAddTo;
		} catch (IOException e) {
			LOG.error("error parsing conf " + name, e);
			throw new RuntimeException(e);
		} catch (DOMException e) {
			LOG.error("error parsing conf " + name, e);
			throw new RuntimeException(e);
		} catch (SAXException e) {
			LOG.error("error parsing conf " + name, e);
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
			LOG.error("error parsing conf " + name, e);
			throw new RuntimeException(e);
		}
	}
}
