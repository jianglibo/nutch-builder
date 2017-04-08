package com.jianglibo.nutchbuilder.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "crawlcat", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class CrawlCat extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String projectRoot;
	
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectRoot() {
		return projectRoot;
	}
	public void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
