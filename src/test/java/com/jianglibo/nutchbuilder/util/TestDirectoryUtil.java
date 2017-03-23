package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class TestDirectoryUtil {

	
	@Test
	public void tStartWith() {
		Path p = DirectoryUtil.findMiddlePathStartsWith(Paths.get("c:/a/b/c/d"), "c");
		assertThat( p, equalTo(Paths.get("c:/a/b/c")));
		p = DirectoryUtil.findMiddlePathStartsWith(Paths.get("c:/a/b/c/d"), "x");
		assertNull(p);
		
		p = DirectoryUtil.findMiddlePathStartsWith(Paths.get("c:/a/b/c/d/c/a"), "c");
		assertThat( p, equalTo(Paths.get("c:/a/b/c/d/c")));
	}
	
	@Test
	public void tTmpDir() {
		String s = System.getProperty("java.io.tmpdir");
		assertThat(Paths.get(s), equalTo(Paths.get("c:/abc")));
	}
}
