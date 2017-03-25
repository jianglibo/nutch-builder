package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.TestUtil;

public class TestNameValue extends Tbase {
	
	@Test
	public void t() throws IOException, TransformerException, SAXException {
		Path nutchSite = Paths.get(applicationConfig.gettProjectRoot()).resolve("conf/nutch-default.xml");
		NameValueConfiguration nvc = new NameValueConfiguration(nutchSite);
		assertThat(nvc.getProperties().getProperty("store.ip.address"), equalTo("false"));
		nvc.SetNameValue("webgui.auth.users", "xxxxxxxxxxxxxxxxxx");
		nvc.writeTo(null);
	}

}
