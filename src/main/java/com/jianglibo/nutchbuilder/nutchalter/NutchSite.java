package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.jianglibo.nutchbuilder.exception.NutchConfigXmlException;
import com.jianglibo.nutchbuilder.util.NameValueConfiguration;

public class NutchSite {
	private final Properties properties = new Properties();
	
	public NutchSite withHbase() {
		properties.put("storage.data.store.class", "org.apache.gora.hbase.store.HBaseStore");
		return this;
	}
	
	public NutchSite withAgentName(String name) {
		properties.put("http.agent.name", name);
		return this;
	}

	public NutchSite withDefaultPlugins() {
		properties.put("plugin.includes", "protocol-http|urlfilter-regex|parse-(html|tika)|index-(basic|anchor)|urlnormalizer-(pass|regex|basic)|scoring-opic");
		return this;
	}

	public NutchSite withFetchThreads(int threads) {
		properties.put("fetcher.threads.fetch", String.valueOf(threads));
		return this;
	}
	
	public NutchSite withFetchInterval(int seconds) {
		properties.put("db.fetch.interval.default", String.valueOf(seconds));
		return this;
	}
	
	public NutchSite withDefaultScheduleClass() {
		properties.put("db.fetch.schedule.class", "org.apache.nutch.crawl.DefaultFetchSchedule");
		return this;
	}
	
	public NutchSite withAdaptiveScheduleClass() {
		properties.put("db.fetch.schedule.class", "org.apache.nutch.crawl.AdaptiveFetchSchedule");
		return this;
	}
	
	public NutchSite withPairs(String...pairs) {
		for(String ps : pairs) {
			String[] ss = ps.split("=");
			if (ss.length == 2) {
				properties.put(ss[0], ss[1]);
			}
		}
		return this;
	}
	
	public void persist(Path resultXml, Path templateXml) throws NutchConfigXmlException {
		try {
			NameValueConfiguration nvc = new NameValueConfiguration(templateXml);
			this.getProperties().forEach((k, v) -> {
				nvc.SetNameValue((String)k, (String)v);
			});
			nvc.writeTo(resultXml);
		} catch (IOException e) {
			throw new NutchConfigXmlException("nutchSite", String.format("possible causes: template not exists %s,  target not exists %s", templateXml.toString(), resultXml.toString()));
		} catch (SAXException e) {
			throw new NutchConfigXmlException("nutchSite", String.format("template is malformed %s", templateXml.toString()));
		} catch (TransformerException e) {
			throw new NutchConfigXmlException("nutchSite", String.format("template is malformed %s", templateXml.toString()));
		}
	}
	

	public Properties getProperties() {
		return properties;
	}
}
