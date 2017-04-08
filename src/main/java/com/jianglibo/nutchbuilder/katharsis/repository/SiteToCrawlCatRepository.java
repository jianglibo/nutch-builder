package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.repository.RelationshipRepositoryBase;

public class SiteToCrawlCatRepository extends RelationshipRepositoryBase<SiteDto, Long, CrawlCatDto, Long> {

	protected SiteToCrawlCatRepository() {
		super(SiteDto.class, CrawlCatDto.class);
	}
}
