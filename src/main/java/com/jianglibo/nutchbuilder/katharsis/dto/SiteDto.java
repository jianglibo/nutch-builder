package com.jianglibo.nutchbuilder.katharsis.dto;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.Site;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.SITE)
@DtoToEntity(entityClass=Site.class)
public class SiteDto extends DtoBase<SiteDto, Site> {

	@Override
	public SiteDto fromEntity(Site entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Site patch(Site entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
