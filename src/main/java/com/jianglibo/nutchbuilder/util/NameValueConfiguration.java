package com.jianglibo.nutchbuilder.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class NameValueConfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(NameValueConfiguration.class);

	private Document document;

	private Path srcXmlFile;
	
	private Properties properties = new Properties();

	public NameValueConfiguration(Path template) throws IOException, SAXException {
		this.srcXmlFile = template;
		parse(template);
		loadResource();
	}

	private void parse(Path srcXmlFile) throws IOException, SAXException {

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

		InputStream is = null;
		try {
			DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
			is = Files.newInputStream(srcXmlFile);
			document = builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public void writeTo(Path dest) throws TransformerException, IOException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();

		DOMSource source = new DOMSource(document);
		StreamResult result;
		if (dest == null) {
			result = new StreamResult(System.out);
		} else {
			result = new StreamResult(Files.newOutputStream(dest));
		}
		transformer.transform(source, result);
	}
	
	public void deleteHbaseDefaultsForVersion() {
		Node n = findNode("hbase.defaults.for.version");
		if (n != null) {
			document.getDocumentElement().removeChild(n);
		}
	}
	
	public NameValueConfiguration withVersionSkip() {
		SetNameValue("hbase.defaults.for.version.skip", "true");
		return this;
	}
	
	public void SetNameValue(String name, String value) {
		Node prop = findNode(name);
		if (prop == null) {
			prop = createNode(name, value);
		}
		NodeList fields = prop.getChildNodes();
		
		for (int j = 0; j < fields.getLength(); j++) {
			Node fieldNode = fields.item(j);
			if (!(fieldNode instanceof Element))
				continue;
			Element field = (Element) fieldNode;
			if ("value".equals(field.getTagName())) {
				field.setTextContent(value);
				break;
			}
		}
	}

//	  <property>
//	  <name>hbase.rootdir</name>
//	  <value>hdfs://nn.intranet.fh.gov.cn:8020/hbase</value>
//	</property>

	private Node createNode(String name, String value) {
		Element pr = document.createElement("property");
		Element ne = document.createElement("name");
		ne.setTextContent(name);
		Element ve = document.createElement("value");
		ve.setTextContent(value);
		pr.appendChild(ne);
		pr.appendChild(ve);
		document.getDocumentElement().appendChild(pr);
		return pr;
	}
	
	private Node findNode(String name) {
		try {
			Element root = document.getDocumentElement();
			NodeList props = root.getChildNodes();
			for (int i = 0; i < props.getLength(); i++) {
				Node propNode = props.item(i);
				if (!(propNode instanceof Element))
					continue;
				Element prop = (Element) propNode;
				NodeList fields = prop.getChildNodes();
				String attr = null;
				for (int j = 0; j < fields.getLength(); j++) {
					Node fieldNode = fields.item(j);
					if (!(fieldNode instanceof Element))
						continue;
					Element field = (Element) fieldNode;
					if ("name".equals(field.getTagName()) && field.hasChildNodes()) {
						attr = ((Text) field.getFirstChild()).getData().trim();
					}
					if (name.equalsIgnoreCase(attr)) {
						return prop;
					}
				}
			}
		} catch (DOMException e) {
			LOG.error("error parsing conf " + srcXmlFile.toString(), e);
			throw new RuntimeException(e);
		}
		return null;
	}

	private void loadResource() {
		try {
			Element root = document.getDocumentElement();
			if (!"configuration".equals(root.getTagName()))
				LOG.error("bad conf file: top-level element not <configuration>");
			NodeList props = root.getChildNodes();
			for (int i = 0; i < props.getLength(); i++) {
				Node propNode = props.item(i);
				if (!(propNode instanceof Element))
					continue;
				Element prop = (Element) propNode;
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
				if (value != null) {
					properties.setProperty(attr, value);
				} else {
//					LOG.error("count null value, key: {}", attr);
				}
			}
		} catch (DOMException e) {
			LOG.error("error parsing conf " + srcXmlFile.toString(), e);
			throw new RuntimeException(e);
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	
}
