package com.jianglibo.nutchbuilder.katharsis.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.UrlFilter;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.URL_FILTER)
@DtoToEntity(entityClass=UrlFilter.class)
public class UrlFilterDto extends DtoBase<UrlFilterDto, UrlFilter> {
	
	
	@NotBlank
	private String regex;
	
	@NotNull
	private MySiteDto mysite;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public MySiteDto getMysite() {
		return mysite;
	}

	public void setMysite(MySiteDto mysite) {
		this.mysite = mysite;
	}

	@Override
	public UrlFilterDto fromEntity(UrlFilter entity) {
		setId(entity.getId());
		setRegex(entity.getRegex());
		setCreatedAt(entity.getCreatedAt());
		setMysite(new MySiteDto().fromEntity(entity.getMysite()));
		return this;
	}

	@Override
	public UrlFilter patch(UrlFilter entity) {
		entity.setRegex(getRegex());
		return entity;
	}
}
