package com.jianglibo.nutchbuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.katharsis.client.internal.ClientDocumentMapper;
import io.katharsis.core.internal.boot.KatharsisBoot;
import io.katharsis.resource.Document;

public abstract class KatharsisBase extends Tbase {
	
	private static String mt = "application/vnd.api+json;charset=UTF-8";
	
	@Autowired
	private KatharsisBoot kboot;
	
	@Value("${katharsis.domainName}")
	private String domainName;
	
	@Value("${katharsis.pathPrefix}")
	private String pathPrefix;

	@Value("${katharsis.default-page-limit}")
	private String pageSize;
	
	
	public <T> List<T> getList(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		ClientDocumentMapper documentMapper = new ClientDocumentMapper(kboot.getResourceRegistry(), objectMapper, null);
		return (List<T>) documentMapper.fromDocument(document, true);
	}
	
	public <T> T getOne(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		ClientDocumentMapper documentMapper = new ClientDocumentMapper(kboot.getResourceRegistry(), objectMapper, null);
		return (T) documentMapper.fromDocument(document, false);
	}
	
	protected String getKatharsisBase() {
		return domainName + pathPrefix;
	}
	
	protected String getBaseURI() {
		return domainName + pathPrefix + "/" + getResourceName();
	}
	
	protected abstract String getResourceName();

	public ResultActions getList() throws Exception {
		String s = getBaseURI();
        MockHttpServletRequestBuilder rhrb = get(s);
        ResultActions ra = mvc.perform(rhrb//
                .contentType(mt)//
                .accept(mt));
        return ra;
	}
}
