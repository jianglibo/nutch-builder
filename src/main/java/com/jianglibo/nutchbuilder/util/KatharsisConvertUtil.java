package com.jianglibo.nutchbuilder.util;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.katharsis.client.internal.ClientDocumentMapper;
import io.katharsis.core.internal.boot.KatharsisBoot;
import io.katharsis.resource.Document;

@Component
public class KatharsisConvertUtil {
	
	@Autowired
	private KatharsisBoot kboot;

	public <T> List<T> getList(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		ClientDocumentMapper documentMapper = new ClientDocumentMapper(kboot.getResourceRegistry(), objectMapper, null);
		return (List<T>) documentMapper.fromDocument(document, true);
	}
	
	public <T> T getOne(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		return getOne(document, targetClass);
	}
	
	public <T> T getOne(Document document, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ClientDocumentMapper documentMapper = new ClientDocumentMapper(kboot.getResourceRegistry(), kboot.getObjectMapper(), null);
		return (T) documentMapper.fromDocument(document, false);
	}
}
