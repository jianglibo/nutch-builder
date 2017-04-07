package com.jianglibo.nutchbuilder.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.InheritableServerClientAndLocalSpanState;
import com.twitter.zipkin.gen.Endpoint;

import io.katharsis.brave.BraveModule;
import io.katharsis.module.Module;
import io.katharsis.repository.filter.AbstractDocumentFilter;
import io.katharsis.repository.filter.DocumentFilter;
import io.katharsis.repository.filter.DocumentFilterChain;
import io.katharsis.repository.filter.DocumentFilterContext;
import io.katharsis.repository.response.Response;
import io.katharsis.validation.ValidationModule;
import zipkin.reporter.Reporter;

@Configuration
public class KatharsisModuleConfig {
	
	@Bean
	public BraveModule braveModule() {
		String serviceName = "exampleApp";
		Endpoint localEndpoint = Endpoint.builder().serviceName(serviceName).build();
		InheritableServerClientAndLocalSpanState spanState = new InheritableServerClientAndLocalSpanState(localEndpoint);
		Brave.Builder builder = new Brave.Builder(spanState);
		builder = builder.reporter(new LoggingReporter());
		Brave brave = builder.build();
		return BraveModule.newServerModule(brave);
	}
	
	@Bean
	public ValidationModule validationModule() {
		return ValidationModule.newInstance();
	}
	
	public final class LoggingReporter implements Reporter<zipkin.Span> {

		private Logger logger = LoggerFactory.getLogger("jianglibo.nutchbuilder.traceReporter");

		@Override
		public void report(zipkin.Span span) {
			logger.info(span.toString());
		}
	}
	
//	@Bean
//	public MyDFilterModule1 myDFilterModule1() {
//		return new MyDFilterModule1();
//	}
//	
//	
//	@Bean
//	public MyDFilterModule myDFilterModule() {
//		return new MyDFilterModule();
//	}
	

	
	
	public static class MyDFilterModule extends AbstractDocumentFilter {

		private DocumentFilterContext filterRequestContext;
		private DocumentFilterChain chain;
		
		@Override
		public Response filter(DocumentFilterContext filterRequestContext, DocumentFilterChain chain) {
			String a = "5";
			return super.filter(filterRequestContext, chain);
		}
		
	}
	
	public static class MyDFilterModule1 extends AbstractDocumentFilter {

		private DocumentFilterContext filterRequestContext;
		private DocumentFilterChain chain;
		
		@Override
		public Response filter(DocumentFilterContext filterRequestContext, DocumentFilterChain chain) {
			String a = "6";
			return super.filter(filterRequestContext, chain);
		}
		
	}
}
