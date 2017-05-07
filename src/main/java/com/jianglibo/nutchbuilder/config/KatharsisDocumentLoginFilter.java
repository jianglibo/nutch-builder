package com.jianglibo.nutchbuilder.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.katharsis.core.internal.boot.KatharsisBoot;
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
	
	@Autowired
	private KatharsisBoot boot;
	
	
	@Override
	public Response filter(DocumentFilterContext filterRequestContext, DocumentFilterChain chain) {
		String a = "7";
		JsonPath jp = filterRequestContext.getJsonPath();
		RepositoryMethodParameterProvider rmpp =  filterRequestContext.getParameterProvider();
		Document d = filterRequestContext.getRequestBody();
		RepositoryMethodParameterProvider rmp = filterRequestContext.getParameterProvider();
		Response r = super.filter(filterRequestContext, chain);
		if (r.getDocument().getMeta() == null) {
			r.getDocument().setMeta(boot.getObjectMapper().createObjectNode());
		}
		return r;
	}
}
