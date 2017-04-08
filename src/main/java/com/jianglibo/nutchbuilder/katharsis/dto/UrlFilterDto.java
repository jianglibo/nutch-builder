package com.jianglibo.nutchbuilder.katharsis.dto;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.UrlFilter;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.URL_FILTER)
@DtoToEntity(entityClass=UrlFilter.class)
public class UrlFilterDto extends DtoBase<UrlFilterDto, UrlFilter> {

	@Override
	public UrlFilterDto fromEntity(UrlFilter entity) {
		return null;
	}

	@Override
	public UrlFilter patch(UrlFilter entity) {
		return null;
	}

}
