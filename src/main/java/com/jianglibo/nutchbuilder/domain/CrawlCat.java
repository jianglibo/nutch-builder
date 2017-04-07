package com.jianglibo.nutchbuilder.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="crawlCat")
	private Set<Site> sites = new HashSet<>();
	
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
	public Set<Site> getSites() {
		return sites;
	}
	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}
}
