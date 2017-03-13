package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.vo.JavaTypeHolder;

public class TestHConfig extends Tbase {
	
	@Autowired
	@Qualifier("xmlObjectMapper")
	private ObjectMapper xmlObjectMapper;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	@Autowired
	private JavaTypeHolder jth;
	
	
	@Test
	public void t() throws JsonParseException, JsonMappingException, IOException {
		Path nutchSite = applicationConfig.getBuildRootPath().resolve("a/conf/nutch-default.xml");
		Map<String, Object> m = xmlObjectMapper.readValue(Files.readAllBytes(nutchSite), xmlObjectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class));
		System.out.print(m);
	}

}
