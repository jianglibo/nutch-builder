package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.util.NameValueConfiguration;

public class TestHbaseSite extends Tbase {
	
	protected static Path neighborProjectRoot = Paths.get("..", "nutch").toAbsolutePath().normalize();
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	
	@Test
	public void t() throws JsonParseException, JsonMappingException, IOException, TransformerException, SAXException {
		HbaseSite hs = new HbaseSite().withRootDir( applicationConfig.getHdfsFullUrlNoLastSlash() + "/user/hbase").withZkQuorum(applicationConfig.getZkQuoram());
		NameValueConfiguration nvc = new NameValueConfiguration(neighborProjectRoot.resolve("conf/hbase-site.xml.template"));
		hs.getProperties().forEach((k, v) -> {
			nvc.SetNameValue((String)k, (String)v);
		});
		nvc.writeTo(neighborProjectRoot.resolve("conf/hbase-site.xml"));
		NameValueConfiguration nvc1 = new NameValueConfiguration(neighborProjectRoot.resolve("conf/hbase-site.xml"));
		assertThat(nvc1.getProperties().get("hbase.rootdir"), equalTo( applicationConfig.getHdfsFullUrlNoLastSlash() + "/user/hbase"));
		assertThat(nvc1.getProperties().get("hbase.zookeeper.quorum"), equalTo(applicationConfig.getZkQuoram()));

	}

}
