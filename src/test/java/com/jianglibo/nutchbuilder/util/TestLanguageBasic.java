package com.jianglibo.nutchbuilder.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
}
