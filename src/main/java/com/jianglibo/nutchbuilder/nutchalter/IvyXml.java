package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import org.springframework.util.Assert;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class IvyXml {
	
	private static final Logger LOG = LoggerFactory.getLogger(IvyXml.class);
	
	private static final Set<String> goraStoreNames = new HashSet<>();
	
	private static final Pattern storePtn = Pattern.compile(".*<dependency\\s+org=\"org.apache.gora\"\\s+name=\"\\S+\"\\s+rev=\"(.*?)\"\\s+.*");
	
	private Node dependenciesNode;
	
	static {
		goraStoreNames.add("gora-sql");
		goraStoreNames.add("gora-accumulo");
		goraStoreNames.add("gora-hbase");
	}

	private Document document;

	private Path srcXmlFile;
	
	public IvyXml(Path template) throws IOException, SAXException {
		this.srcXmlFile = template;
		parse(template);
	}
	
	public IvyXml addDependency(String org, String name, String rev) throws IOException {
		Node n = findDependency(org, name, rev);
		if (n == null) {
			Element de = document.createElement("dependency");
			de.setAttribute("org", org);
			de.setAttribute("name", name);
			de.setAttribute("rev", rev);
			de.setAttribute("conf", "*->default");
			dependenciesNode.insertBefore(de, dependenciesNode.getFirstChild());
		}
		return this;
	}
	
	public IvyXml alterStoreDependency(String org,String name) throws IOException {
		Assert.isTrue(goraStoreNames.contains(name), "Invalid Gora store dependency.");
		String rev = parseRev(name);
		Assert.notNull(rev, "Can't find depedency rev.");
		Node n = findDependency(org, name);
		if (n == null) {
			Element de = document.createElement("dependency");
			de.setAttribute("org", org);
			de.setAttribute("name", name);
			de.setAttribute("rev", rev);
			de.setAttribute("conf", "*->default");
			dependenciesNode.insertBefore(de, dependenciesNode.getFirstChild());
		}
		return this;
	}
	
	private String parseRev(String storeDepName) throws IOException {
		List<String> lines = Files.readAllLines(srcXmlFile);
		List<String> mlines = lines.stream().filter(l -> l.contains("\"" + storeDepName + "\"")).collect(Collectors.toList());
		
		for(String line: mlines) {
			Matcher m = storePtn.matcher(line);
			if (m.matches()) {
				return m.group(1);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param attrs, the order are org,name,rev
	 * @return
	 */
	private Node findDependency(String...attrs) {
		int attrLen = attrs.length;
		Assert.isTrue(attrLen > 1 ,"parameter count is 2 at least");
		try {
			Element root = document.getDocumentElement();
			NodeList props = root.getChildNodes();
			for (int i = 0; i < props.getLength(); i++) {
				Node propNode = props.item(i);
				if (!(propNode instanceof Element))
					continue;
				Element prop = (Element) propNode;
				if ("dependencies".equals(prop.getTagName())) {
					dependenciesNode = prop;
					NodeList dependencies = prop.getChildNodes();
					for(int j = 0; j < dependencies.getLength(); j++) {
						Node fieldNode = dependencies.item(j);
						if (!(fieldNode instanceof Element))
							continue;
						Element field = (Element) fieldNode;
						String org = field.getAttribute("org");
						String name = field.getAttribute("name");
						String rev = field.getAttribute("rev");
						if (attrs[0].equals(org) && attrs[1].equals(name) && attrLen == 2) {
							return field;
						}
						
						if (attrs[0].equals(org) && attrs[1].equals(name) && attrs[2].equals(rev) && attrLen == 3) {
							return field;
						}
					}
				} else {
					continue;
				}
			}
		} catch (DOMException e) {
			LOG.error("error parsing conf " + srcXmlFile.toString(), e);
			throw new RuntimeException(e);
		}
		return null;
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
	
	
	
	private void parse(Path srcXmlFile) throws IOException, SAXException {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		// ignore all comments inside the xml file
//		docBuilderFactory.setIgnoringComments(true);

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
}
