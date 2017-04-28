package com.jianglibo.nutchbuilder.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JsonApiUrlBuilder {

	private Map<String, Object> url = new HashMap<>();
	
	private final String baseUrl;
	
	public JsonApiUrlBuilder(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public JsonApiUrlBuilder withInclude(String includeValue) {
		url.put("include", includeValue);
		return this;
	}
	
	public String build() {
		StringBuilder sb = new StringBuilder(baseUrl);
		if (sb.indexOf("?") == -1) {
			sb.append('?');
		}
		
		for(Entry<String, Object> en: url.entrySet()) {
			sb.append('&').append(en.getKey()).append("=").append(en.getValue());
		}
		return sb.toString().replaceAll("\\?&", "?");
	}
	
}
