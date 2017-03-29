package com.jianglibo.nutchbuilder.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.InheritableServerClientAndLocalSpanState;
import com.twitter.zipkin.gen.Endpoint;

import io.katharsis.brave.BraveModule;
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
	
	public final class LoggingReporter implements Reporter<zipkin.Span> {

		private Logger logger = LoggerFactory.getLogger("jianglibo.nutchbuilder.traceReporter");

		@Override
		public void report(zipkin.Span span) {
			logger.info(span.toString());
		}
	}
}
