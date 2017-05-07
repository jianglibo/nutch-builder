package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.MySite;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.MY_SITE)
@DtoToEntity(entityClass=MySite.class)
public class MySiteDto extends DtoBase<MySiteDto, MySite> {

	@NotBlank
	private String homepage;
	
	private String cburl;
	
	private boolean cburlVerified;
	
	private String cbsecret;
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.LAZY, opposite="site")
	private List<UrlFilterDto> urlfilters = new ArrayList<>();
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.EAGER, opposite="mysites")
	private UserDto creator;
	
	@Override
	public MySiteDto fromEntity(MySite entity) {
		setCbsecret(entity.getCbsecret());
		setCburl(entity.getCburl());
		setCreatedAt(entity.getCreatedAt());
		setHomepage(entity.getHomepage());
		setId(entity.getId());
		return this;
	}

	@Override
	public MySite patch(MySite entity) {
		entity.setCbsecret(cbsecret);
		entity.setCburl(getCburl());
		entity.setHomepage(getHomepage());
		return entity;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
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

	public List<UrlFilterDto> getUrlfilters() {
		return urlfilters;
	}

	public void setUrlfilters(List<UrlFilterDto> urlfilters) {
		this.urlfilters = urlfilters;
	}

	public UserDto getCreator() {
		return creator;
	}

	public void setCreator(UserDto creator) {
		this.creator = creator;
	}

}
