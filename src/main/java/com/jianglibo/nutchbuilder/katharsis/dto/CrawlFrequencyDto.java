package com.jianglibo.nutchbuilder.katharsis.dto;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlFrequency;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.CRAWL_FRE)
@DtoToEntity(entityClass=CrawlFrequency.class)
public class CrawlFrequencyDto extends DtoBase<CrawlFrequencyDto, CrawlFrequency> {

	@Override
	public CrawlFrequencyDto fromEntity(CrawlFrequency entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CrawlFrequency patch(CrawlFrequency entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
