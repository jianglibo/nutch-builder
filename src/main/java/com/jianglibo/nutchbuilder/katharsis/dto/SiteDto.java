package com.jianglibo.nutchbuilder.katharsis.dto;

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
	
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.EAGER)
	@NotNull
	private CrawlCatDto crawlCat;
	
	@Override
	public SiteDto fromEntity(Site entity) {
		setCbsecret(entity.getCbsecret());
		setCburl(entity.getCburl());
		setCreatedAt(entity.getCreatedAt());
		setHomeUrl(entity.getHomeUrl());
		setId(entity.getId());
		setCrawlCat(new CrawlCatDto().fromEntity(entity.getCrawlCat()));
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

}
