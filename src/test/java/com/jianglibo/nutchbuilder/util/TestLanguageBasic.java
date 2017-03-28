package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.SortedMap;

import org.junit.Test;

public class TestLanguageBasic {
	
	@Test
	public void tenv() {
		String e = System.getenv("HBASE_HOME");
		assertNotNull("HBASE_HOME should not null.", e);
		e = System.getenv("HBASE_HOME_1");
		assertNull("HBASE_HOME_1 should be null.", e);
		e = System.getProperty("HBASE_HOME");
		assertNull("HBASE_HOME should be null by property.", e);
	}
	
	
	@Test
	public void tLocal() {
		Charset cs = Charset.defaultCharset();
		assertThat(cs.name(), equalTo("UTF-8"));
		
		Locale lo = Locale.getDefault();
		SortedMap<String, Charset> cm = Charset.availableCharsets();
		System.out.println("available charsets: " + cm.size());
		cm.entrySet().stream().forEach(kv -> System.out.println(kv.getKey() + "=" + kv.getValue()));
		lo.getLanguage();
//		assertThat(lo.getLanguage(), equalTo("abc"));
		
//		assertThat(Locale.getAvailableLocales().length, equalTo(100));
	}
}
