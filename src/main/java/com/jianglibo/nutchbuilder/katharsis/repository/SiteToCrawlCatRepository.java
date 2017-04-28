package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class SiteToCrawlCatRepository extends RelationshipRepositoryBase<SiteDto, Long, CrawlCatDto, Long> {

	protected SiteToCrawlCatRepository() {
		super(SiteDto.class, CrawlCatDto.class);
	}
}
