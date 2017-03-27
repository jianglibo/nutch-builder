package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.TransformerException;

import org.junit.After;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.exception.HbaseSiteXmlException;
import com.jianglibo.nutchbuilder.util.NameValueConfiguration;

public class TestHbaseSite extends Tbase {
	
	private Path tp;
	
	@After
	public void a() throws IOException {
		if (tp != null && Files.exists(tp)) {
			Files.delete(tp);
		}
	}
	
	
	@Test
	public void t() throws JsonParseException, JsonMappingException, IOException, TransformerException, SAXException, HbaseSiteXmlException {
		tp = Files.createTempFile("hbasesitetest", "xml");
		
		HbaseSite hs = new HbaseSite().withRootDir( applicationConfig.getHdfsFullUrlNoLastSlash() + "/user/hbase").withZkQuorum(applicationConfig.getZkQuoram());
		hs.persist(tp, StepBase.neighborProjectRoot.resolve("conf/hbase-site.xml.template"));
		
		NameValueConfiguration nvc1 = new NameValueConfiguration(tp);
		assertThat(nvc1.getProperties().get("hbase.rootdir"), equalTo( applicationConfig.getHdfsFullUrlNoLastSlash() + "/user/hbase"));
		assertThat(nvc1.getProperties().get("hbase.zookeeper.quorum"), equalTo(applicationConfig.getZkQuoram()));

	}
	
	@Test
	public void tFromEnv() throws IOException, HbaseSiteXmlException {
		tp = Files.createTempFile("hbasesitetest", "xml");
		new HbaseSite().fromHbaseHomeEnv(tp);
		Files.lines(tp).forEach(System.out::println);
	}

}
