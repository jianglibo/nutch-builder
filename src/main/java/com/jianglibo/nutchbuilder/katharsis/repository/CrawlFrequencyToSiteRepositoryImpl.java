package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class CrawlFrequencyToSiteRepositoryImpl extends RelationshipRepositoryBase<CrawlFrequencyDto, Long, SiteDto, Long> {

    protected CrawlFrequencyToSiteRepositoryImpl() {
		super(CrawlFrequencyDto.class, SiteDto.class);
	}
}
