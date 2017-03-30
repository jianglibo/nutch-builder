package com.jianglibo.nutchbuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public abstract class KatharsisBase extends Tbase {
	
	private static String mt = "application/vnd.api+json;charset=UTF-8";
	
	@Value("${katharsis.domainName}")
	private String domainName;
	
	@Value("${katharsis.pathPrefix}")
	private String pathPrefix;

	@Value("${katharsis.default-page-limit}")
	private String pageSize;
	
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
