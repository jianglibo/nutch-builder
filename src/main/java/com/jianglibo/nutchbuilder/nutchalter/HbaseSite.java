package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.jianglibo.nutchbuilder.exception.HbaseSiteXmlException;
import com.jianglibo.nutchbuilder.util.NameValueConfiguration;

public class HbaseSite {

	private final Properties properties = new Properties();
	
	public HbaseSite withRootDir(String rootDir) {
		properties.put("hbase.rootdir", rootDir);
		return this;
	}
	
	public HbaseSite withZkQuorum(String zkQuorum) {
		properties.put("hbase.zookeeper.quorum", zkQuorum);
		return this;
	}

	public Properties getProperties() {
		return properties;
	}
	
	public void persist(Path resultXml, Path templateXml) throws HbaseSiteXmlException {
		try {
			NameValueConfiguration nvc = new NameValueConfiguration(templateXml);
			this.getProperties().forEach((k, v) -> {
				nvc.SetNameValue((String)k, (String)v);
			});
			nvc.writeTo(resultXml);
		} catch (IOException e) {
			throw new HbaseSiteXmlException("hbaseSiteFromEnv", String.format("possible causes: template not exists %s,  target not exists %s", templateXml.toString(), resultXml.toString()));
		} catch (SAXException e) {
			throw new HbaseSiteXmlException("hbaseSiteFromEnv", String.format("template is malformed %s", templateXml.toString()));
		} catch (TransformerException e) {
			throw new HbaseSiteXmlException("hbaseSiteFromEnv", String.format("template is malformed %s", templateXml.toString()));
		}
	}
	
	public void fromHbaseHomeEnv(Path resultXml) throws HbaseSiteXmlException {
		String e = System.getenv("HBASE_HOME");
		if (e == null) {
			throw new HbaseSiteXmlException("hbaseSiteFromEnv", "no HBASE_HOME env exists.");
		}
		Path xf = Paths.get(e, "conf", "hbase-site.xml");
		List<String> lines;
		try {
			lines = Files.readAllLines(xf);
		} catch (IOException e2) {
			throw new HbaseSiteXmlException("hbaseSiteFromEnv", String.format("HBASE_HOME env do exists but the file it point to %s is not exists", resultXml.toString()));
		}
		try {
			Files.write(resultXml, lines);
		} catch (Exception e1) {
			throw new HbaseSiteXmlException("hbaseSiteFromEnv", String.format("write to target %s failed.", e, resultXml.toString()));
		}
	}
}
