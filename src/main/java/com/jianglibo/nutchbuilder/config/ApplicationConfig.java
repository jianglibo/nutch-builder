package com.jianglibo.nutchbuilder.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Priority;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myapp")
@Priority(1)
public class ApplicationConfig implements InitializingBean {
	
	private String dataWriteSourcePath;
	
	private String templateRoot;
	
	private Path templateRootPath;
	
	private boolean disableCsrf;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.templateRootPath = Paths.get(getTemplateRoot()).normalize().toAbsolutePath();
	}

	public String getOutSideBaseUrl() {
		return null;
	}
	
	public String getDataWriteSourcePath() {
		return dataWriteSourcePath;
	}

	public void setDataWriteSourcePath(String dataWriteSourcePath) {
		this.dataWriteSourcePath = dataWriteSourcePath;
	}


	public String getTemplateRoot() {
		return templateRoot;
	}

	public void setTemplateRoot(String templateRoot) {
		this.templateRoot = templateRoot;
	}

	public Path getTemplateRootPath() {
		return templateRootPath;
	}

	public boolean isDisableCsrf() {
		return disableCsrf;
	}

	public void setDisableCsrf(boolean disableCsrf) {
		this.disableCsrf = disableCsrf;
	}

}
