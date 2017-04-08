package com.jianglibo.nutchbuilder.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

@Entity
@Table(name = "site")
public class Site extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * urlfilter must contain homeurl. 
	 */
	private String homeUrl;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private CrawlCat crawlCat;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="site")
	private Set<UrlFilter> urlfilters = new HashSet<>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="site")
	private Set<CrawlFrequency> crawlFrequencies = new HashSet<>();
	
	private String cburl;
	
	private boolean cburlVerified;
	
	private String cbsecret;

	public CrawlCat getCrawlCat() {
		return crawlCat;
	}

	public void setCrawlCat(CrawlCat crawlCat) {
		this.crawlCat = crawlCat;
	}

	public Set<UrlFilter> getUrlfilters() {
		return urlfilters;
	}

	public void setUrlfilters(Set<UrlFilter> urlfilters) {
		this.urlfilters = urlfilters;
	}

	public Set<CrawlFrequency> getCrawlFrequencies() {
		return crawlFrequencies;
	}

	public void setCrawlFrequencies(Set<CrawlFrequency> crawlFrequencies) {
		this.crawlFrequencies = crawlFrequencies;
	}

	public String getCburl() {
		return cburl;
	}

	public void setCburl(String cburl) {
		this.cburl = cburl;
	}

	public boolean isCburlVerified() {
		return cburlVerified;
	}

	public void setCburlVerified(boolean cburlVerified) {
		this.cburlVerified = cburlVerified;
	}

	public String getCbsecret() {
		return cbsecret;
	}

	public void setCbsecret(String cbsecret) {
		this.cbsecret = cbsecret;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}
	
	public SiteDto toDto() {
		SiteDto sdto = new SiteDto();
		sdto.setCbsecret(getCbsecret());
		sdto.setCburl(getCburl());
		sdto.setCreatedAt(getCreatedAt());
		sdto.setHomeUrl(getHomeUrl());
		sdto.setId(getId());
		sdto.setCrawlCat(new CrawlCatDto().fromEntity(getCrawlCat()));
		return sdto;
	}

}
