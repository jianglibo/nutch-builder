package com.jianglibo.nutchbuilder.katharsis.dto;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlCat;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.CRAWL_CAT)
@DtoToEntity(entityClass=CrawlCat.class)
public class CrawlCatDto extends DtoBase<CrawlCatDto, CrawlCat>{
	
	private String name;
	
	private String projectRoot;
	
	private String description;

	@Override
	public CrawlCatDto fromEntity(CrawlCat entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CrawlCat patch(CrawlCat entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
