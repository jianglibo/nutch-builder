package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.junit.Assume;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.jianglibo.nutchbuilder.TestUtil;
import com.jianglibo.nutchbuilder.nutchalter.AntBuild.Build;
import com.jianglibo.nutchbuilder.nutchalter.GoraProperties.StoreType;
import com.jianglibo.nutchbuilder.util.NameValueConfiguration;

public class TestAlterGoraProperties {
	
	private String tplName = "nutchnewest";
	
	private boolean skipBuildTest = true;
	
	@Test
	public void tPtn() {
		assertTrue("should match comment outed", GoraProperties.datastorePtn.matcher("#gora.datastore.default=org.apache.gora.mock.store.MockDataStore").find());
		assertTrue("should match uncommented", GoraProperties.datastorePtn.matcher("gora.datastore.default=org.apache.gora.mock.store.MockDataStore").find());
	}
	
	private void copyIfNotExists() throws IOException {
		if (!Files.exists(TestUtil.tProjectRoot)) {
			OracleCopy.copyTree(Paths.get("e:/nutchBuilderRoot/templateRoot", tplName), TestUtil.tProjectRoot);
		}
		assertTrue("copyed directory should right", Files.exists(TestUtil.tProjectRoot.resolve("ivy")));
	}
	
	@Test
	public void testIvyXml() throws TransformerException, IOException, SAXException {
		Path ivyPath = TestUtil.tProjectRoot.resolve("ivy/ivy.xml");
		IvyXml ix = new IvyXml(ivyPath).alterStoreDependency("org.apache.gora", "gora-hbase").addDependency("org.apache.hbase", "hbase-common", "0.98.19-hadoop2");
		ix.writeTo(ivyPath);
	}
	
	@Test
	public void testOracleCopy() throws IOException {
		copyIfNotExists();
		GoraProperties.alterGoraProperties(TestUtil.tProjectRoot, StoreType.HBaseStore);
		List<String> lines = Files.readAllLines(TestUtil.tProjectRoot.resolve("conf/gora.properties"));
		List<String> changed = new ArrayList<>();
		for(String li: lines) {
			if (GoraProperties.datastorePtn.matcher(li).find()) {
				changed.add(li);
			}
		}
		assertThat("should be only one line", changed.size(), equalTo(1));
		assertThat("should be contented with", changed.get(0), equalTo("gora.datastore.default=org.apache.gora.hbase.store.HBaseStore"));
	}
	
	@Test
	public void testBuild() throws Exception {
		Assume.assumeFalse(skipBuildTest);
		copyIfNotExists();
		Build bd = new AntBuild.Build(TestUtil.tProjectRoot, TestUtil.properties.getProperty("antExec"), "runtime");
		int i = bd.call();
		assertThat(i, equalTo(0));
		assertTrue("log file should exists.", Files.exists(bd.getPblog()));
	}
	
	@Test
	public void testNutchSite() throws IOException, SAXException, TransformerException {
		NutchSite ns = new NutchSite();
		ns.withAdaptiveScheduleClass()
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
					"db.update.additions.allowed=true");
		NameValueConfiguration nvc = new NameValueConfiguration(TestUtil.tProjectRoot.resolve("conf/nutch-default.xml"));
		ns.getProperties().forEach((k, v) -> {
			nvc.SetNameValue((String)k, (String)v);
		});
		
		nvc.writeTo(TestUtil.tProjectRoot.resolve("conf/nutch-site.xml"));
		
		NameValueConfiguration nvc1 = new NameValueConfiguration(TestUtil.tProjectRoot.resolve("conf/nutch-site.xml"));
		assertThat(nvc1.getProperties().get("http.agent.name"), equalTo("fhgov"));
	}
	
	@Test
	public void testHbaseSite() throws IOException, SAXException, TransformerException {
		HbaseSite hs = new HbaseSite().withRootDir("hdfs://s62.host.name:/user/hbase").withZkQuorum("s62.host.name,s63.host.name,s64.host.name,s65.host.name,s66.host.name");
		NameValueConfiguration nvc = new NameValueConfiguration(TestUtil.tProjectRoot.resolve("conf/hbase-default-1.2.4.xml"));
		hs.getProperties().forEach((k, v) -> {
			nvc.SetNameValue((String)k, (String)v);
		});
		nvc.withVersionSkip();
		nvc.writeTo(TestUtil.tProjectRoot.resolve("conf/hbase-site.xml"));
		NameValueConfiguration nvc1 = new NameValueConfiguration(TestUtil.tProjectRoot.resolve("conf/hbase-site.xml"));
		assertThat(nvc1.getProperties().get("hbase.rootdir"), equalTo("hdfs://s62.host.name:/user/hbase"));
		assertThat(nvc1.getProperties().get("hbase.zookeeper.quorum"), equalTo("s62.host.name,s63.host.name,s64.host.name,s65.host.name,s66.host.name"));

	}
	
	@Test
	public void tRegexUrlFilterConf() throws IOException {
		copyIfNotExists();
		RegexUrlFilterConfFile rfcf = new RegexUrlFilterConfFile().withDefaultSkips().addAccept("+^http://www.fh.gov.cn/.*");
		rfcf.alter(TestUtil.tProjectRoot);
		
	}
}
