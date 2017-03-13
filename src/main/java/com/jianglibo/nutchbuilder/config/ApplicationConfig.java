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
	
	private String buildRoot;
	
	private Path templateRootPath;
	
	private Path buildRootPath;
	
	private boolean disableCsrf;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.templateRootPath = Paths.get(getTemplateRoot()).normalize().toAbsolutePath();
		this.setBuildRootPath(Paths.get(getBuildRoot()).normalize().toAbsolutePath());
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

	public String getBuildRoot() {
		return buildRoot;
	}

	public void setBuildRoot(String buildRoot) {
		this.buildRoot = buildRoot;
	}

	public Path getBuildRootPath() {
		return buildRootPath;
	}

	public void setBuildRootPath(Path buildRootPath) {
		this.buildRootPath = buildRootPath;
	}

}
