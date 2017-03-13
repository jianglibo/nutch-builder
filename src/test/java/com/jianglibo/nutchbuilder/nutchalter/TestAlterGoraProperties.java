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

import org.junit.Test;

import com.jianglibo.nutchbuilder.TestUtil;
import com.jianglibo.nutchbuilder.nutchalter.AntBuild.Build;
import com.jianglibo.nutchbuilder.nutchalter.GoraProperties.StoreType;

public class TestAlterGoraProperties {
	
	private String tplName = "nutchnewest";
	
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
	public void test() throws Exception {
		copyIfNotExists();
		Build bd = new AntBuild.Build(TestUtil.tProjectRoot, TestUtil.properties.getProperty("antExec"), "runtime");
		int i = bd.call();
		assertThat(i, equalTo(0));
		assertTrue("log file should exists.", Files.exists(bd.getPblog()));
	}
	
	@Test
	public void tRegexUrlFilterConf() throws IOException {
		copyIfNotExists();
		RegexUrlFilterConfFile rfcf = new RegexUrlFilterConfFile().withDefaultSkips().addAccept("+^http://www.fh.gov.cn/.*");
		rfcf.alter(TestUtil.tProjectRoot);
		
	}
}
