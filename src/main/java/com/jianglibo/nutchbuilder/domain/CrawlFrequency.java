package com.jianglibo.nutchbuilder.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "crawlfrequency", uniqueConstraints = { @UniqueConstraint(columnNames = {"regex", "seconds", "SITE_ID"}) })
public class CrawlFrequency extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regex;
	private int seconds;
	
	@ManyToOne
	@JoinColumn(name="SITE_ID", nullable=false)
	private Site site;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
