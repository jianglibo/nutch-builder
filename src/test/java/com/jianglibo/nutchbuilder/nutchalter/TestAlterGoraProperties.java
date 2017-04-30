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

import org.junit.Test;
import org.xml.sax.SAXException;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.nutchalter.GoraProperties.StoreType;

public class TestAlterGoraProperties extends Tbase {
	
	private String tplName = "nutchnewest";
	
	private boolean skipBuildTest = true;
	
	@Test
	public void tPtn() {
		assertTrue("should match comment outed", GoraProperties.datastorePtn.matcher("#gora.datastore.default=org.apache.gora.mock.store.MockDataStore").find());
		assertTrue("should match uncommented", GoraProperties.datastorePtn.matcher("gora.datastore.default=org.apache.gora.mock.store.MockDataStore").find());
	}
	
	private void copyIfNotExists() throws IOException {
		if (!Files.exists(getTprojectRoot())) {
			JdkCopy.copyTree(Paths.get("e:/nutchBuilderRoot/templateRoot", tplName), getTprojectRoot());
		}
		assertTrue("copyed directory should right", Files.exists(getTprojectRoot().resolve("ivy")));
	}
	
	@Test
	public void testIvyXml() throws TransformerException, IOException, SAXException {
		Path ivyPath = getTprojectRoot().resolve("ivy/ivy.xml");
		IvyXml ix = new IvyXml(ivyPath).alterStoreDependency("org.apache.gora", "gora-hbase").addDependency("org.apache.hbase", "hbase-common", "0.98.19-hadoop2");
		ix.writeTo(ivyPath);
	}
	
	@Test
	public void testOracleCopy() throws IOException {
		copyIfNotExists();
		GoraProperties.alterGoraProperties(getTprojectRoot(), StoreType.HBaseStore);
		List<String> lines = Files.readAllLines(getTprojectRoot().resolve("conf/gora.properties"));
		List<String> changed = new ArrayList<>();
		for(String li: lines) {
			if (GoraProperties.datastorePtn.matcher(li).find()) {
				changed.add(li);
			}
		}
		assertThat("should be only one line", changed.size(), equalTo(1));
		assertThat("should be contented with", changed.get(0), equalTo("gora.datastore.default=org.apache.gora.hbase.store.HBaseStore"));
	}
	

	
	private Path getTprojectRoot() {
		return Paths.get(applicationConfig.gettProjectRoot());
	}
	
	@Test
	public void tRegexUrlFilterConf() throws IOException {
		copyIfNotExists();
		RegexUrlFilterConfFile rfcf = new RegexUrlFilterConfFile().withDefaultSkips().addAccept("+^http://www.fh.gov.cn/.*");
		rfcf.persist(getTprojectRoot());
		
	}
}
