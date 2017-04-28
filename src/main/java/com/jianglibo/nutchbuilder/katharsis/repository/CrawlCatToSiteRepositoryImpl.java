package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class CrawlCatToSiteRepositoryImpl extends RelationshipRepositoryBase<CrawlCatDto, Long, SiteDto, Long> {

    protected CrawlCatToSiteRepositoryImpl() {
		super(CrawlCatDto.class, SiteDto.class);
	}
}
