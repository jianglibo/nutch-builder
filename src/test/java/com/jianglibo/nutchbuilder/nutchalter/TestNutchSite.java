package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.exception.NutchConfigXmlException;
import com.jianglibo.nutchbuilder.util.NameValueConfiguration;

public class TestNutchSite extends Tbase {

	@Test
	public void testNutchSite() throws IOException, SAXException, TransformerException, NutchConfigXmlException {
		Path xmlPath = StepBase.neighborProjectRoot.resolve("conf/nutch-site.xml");
		new NutchSite()
			.withAdaptiveScheduleClass()
			.withAgentName("fhgov")
			.withDefaultPlugins()
			.withFetchInterval(900)
			.withFetchThreads(10)
			.withHbase()
			.withPairs("db.fetch.schedule.adaptive.inc_rate=0.4",
					"db.fetch.schedule.adaptive.dec_rate=0.2",
					"db.fetch.schedule.adaptive.min_interval=60",
					"db.fetch.schedule.adaptive.max_interval=31536000",
					"db.fetch.schedule.adaptive.sync_delta=true",
					"db.fetch.schedule.adaptive.sync_delta_rate=0.3",
					"db.update.additions.allowed=true").persist(xmlPath, xmlPath);
		
		NameValueConfiguration nvc1 = new NameValueConfiguration(xmlPath);
		assertThat(nvc1.getProperties().get("http.agent.name"), equalTo("fhgov"));
	}
	
}
