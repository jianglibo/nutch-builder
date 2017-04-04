package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.SortedMap;
import java.util.regex.Pattern;

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
	public void tp() {
		Pattern ptn = Pattern.compile("^/jsonapi/loginAttempts/?.*");
		
		assertTrue(ptn.matcher("/jsonapi/loginAttemptsabcdefged").matches());
		assertTrue(ptn.matcher("/jsonapi/loginAttempts").matches());
		assertTrue(ptn.matcher("/jsonapi/loginAttempts/").matches());
		assertTrue(ptn.matcher("/jsonapi/loginAttempts/555").matches());
		
		ptn = Pattern.compile(String.format("%s/.*|%s", "^/jsonapi/loginAttempts", "^/jsonapi/loginAttempts"));
		
		
//		assertTrue(ptn.matcher("/jsonapi/loginAttemptsabcdefged").matches());
		assertTrue(ptn.matcher("/jsonapi/loginAttempts").matches());
		assertTrue(ptn.matcher("/jsonapi/loginAttempts/").matches());
		assertTrue(ptn.matcher("/jsonapi/loginAttempts/555").matches());

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
