package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.alter.RelationshipRepositoryBaseMy;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

@Component
public class CrawlCatToSiteRepositoryImpl extends RelationshipRepositoryBaseMy<CrawlCatDto, SiteDto> {

    protected CrawlCatToSiteRepositoryImpl() {
		super(CrawlCatDto.class, SiteDto.class);
	}
}
