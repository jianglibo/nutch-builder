package com.jianglibo.nutchbuilder.config;

import org.springframework.stereotype.Component;

import io.katharsis.core.internal.dispatcher.path.JsonPath;
import io.katharsis.legacy.internal.RepositoryMethodParameterProvider;
import io.katharsis.repository.filter.AbstractDocumentFilter;
import io.katharsis.repository.filter.DocumentFilterChain;
import io.katharsis.repository.filter.DocumentFilterContext;
import io.katharsis.repository.response.Response;
import io.katharsis.resource.Document;

@Component
public class KatharsisDocumentLoginFilter extends AbstractDocumentFilter {
	private DocumentFilterContext filterRequestContext;
	private DocumentFilterChain chain;
	
	@Override
	public Response filter(DocumentFilterContext filterRequestContext, DocumentFilterChain chain) {
		String a = "7";
		JsonPath jp = filterRequestContext.getJsonPath();
		Document d = filterRequestContext.getRequestBody();
		RepositoryMethodParameterProvider rmp = filterRequestContext.getParameterProvider();
		Response r = super.filter(filterRequestContext, chain); 
		return r;
	}
}
