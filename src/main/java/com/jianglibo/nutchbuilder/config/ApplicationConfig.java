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
	
	private String hdfsHost;
	
	private String hdfsPort;
	
	private String antExec;
	
	private String tProjectRoot;
	
	private String hadoopExecutable;
	
	private String hbaseRestHost;
	private int hbaseRestPort;
	private String hbaseRestProtocol;
	
	private boolean disableCsrf;
	
	private String hbaseRestPrefix;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.templateRootPath = Paths.get(getTemplateRoot()).normalize().toAbsolutePath();
		this.setBuildRootPath(Paths.get(getBuildRoot()).normalize().toAbsolutePath());
		this.setHbaseRestPrefix(String.format("%s://%s:%s", getHbaseRestProtocol(), getHbaseRestHost(), getHbaseRestPort()));
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

	public String getHbaseRestHost() {
		return hbaseRestHost;
	}

	public void setHbaseRestHost(String hbaseRestHost) {
		this.hbaseRestHost = hbaseRestHost;
	}

	public int getHbaseRestPort() {
		return hbaseRestPort;
	}

	public void setHbaseRestPort(int hbaseRestPort) {
		this.hbaseRestPort = hbaseRestPort;
	}

	public void setTemplateRootPath(Path templateRootPath) {
		this.templateRootPath = templateRootPath;
	}

	public String getHbaseRestProtocol() {
		return hbaseRestProtocol;
	}

	public void setHbaseRestProtocol(String hbaseRestProtocol) {
		this.hbaseRestProtocol = hbaseRestProtocol;
	}

	public String getHbaseRestPrefix() {
		return hbaseRestPrefix;
	}

	public void setHbaseRestPrefix(String hbaseRestPrefix) {
		this.hbaseRestPrefix = hbaseRestPrefix;
	}

	public String getHadoopExecutable() {
		return hadoopExecutable;
	}

	public void setHadoopExecutable(String hadoopExecutable) {
		this.hadoopExecutable = hadoopExecutable;
	}

	public String getHdfsHost() {
		return hdfsHost;
	}

	public void setHdfsHost(String hdfsHost) {
		this.hdfsHost = hdfsHost;
	}

	public String getHdfsPort() {
		return hdfsPort;
	}

	public void setHdfsPort(String hdfsPort) {
		this.hdfsPort = hdfsPort;
	}

	public String getAntExec() {
		return antExec;
	}

	public void setAntExec(String antExec) {
		this.antExec = antExec;
	}

	public String gettProjectRoot() {
		return tProjectRoot;
	}

	public void settProjectRoot(String tProjectRoot) {
		this.tProjectRoot = tProjectRoot;
	}
	
	
}
