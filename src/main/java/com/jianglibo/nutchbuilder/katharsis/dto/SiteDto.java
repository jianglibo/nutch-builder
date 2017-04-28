package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.Site;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.SITE)
@DtoToEntity(entityClass=Site.class)
public class SiteDto extends DtoBase<SiteDto, Site> {

	@NotBlank
	private String homeUrl;
	
	private String cburl;
	
	private boolean cburlVerified;
	
	private String cbsecret;
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.LAZY, opposite="site")
	private List<UrlFilterDto> urlfilters = new ArrayList<>();
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.LAZY, opposite="site")
	private List<CrawlFrequencyDto> crawlFrenquencies = new ArrayList<>();
	
	@NotNull
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.EAGER, opposite="sites")
	private CrawlCatDto crawlCat;
	
	@NotNull
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.EAGER, opposite="sites")
	private UserDto creator;
	
	@Override
	public SiteDto fromEntity(Site entity) {
		setCbsecret(entity.getCbsecret());
		setCburl(entity.getCburl());
		setCreatedAt(entity.getCreatedAt());
		setHomeUrl(entity.getHomeUrl());
		setId(entity.getId());
		return this;
	}

	@Override
	public Site patch(Site entity) {
		entity.setCbsecret(cbsecret);
		entity.setCburl(getCburl());
		entity.setHomeUrl(getHomeUrl());
		return entity;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
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

	public CrawlCatDto getCrawlCat() {
		return crawlCat;
	}

	public void setCrawlCat(CrawlCatDto crawlCat) {
		this.crawlCat = crawlCat;
	}

	public List<UrlFilterDto> getUrlfilters() {
		return urlfilters;
	}

	public void setUrlfilters(List<UrlFilterDto> urlfilters) {
		this.urlfilters = urlfilters;
	}

	public List<CrawlFrequencyDto> getCrawlFrenquencies() {
		return crawlFrenquencies;
	}

	public void setCrawlFrenquencies(List<CrawlFrequencyDto> crawlFrenquencies) {
		this.crawlFrenquencies = crawlFrenquencies;
	}

	public UserDto getCreator() {
		return creator;
	}

	public void setCreator(UserDto creator) {
		this.creator = creator;
	}

}
