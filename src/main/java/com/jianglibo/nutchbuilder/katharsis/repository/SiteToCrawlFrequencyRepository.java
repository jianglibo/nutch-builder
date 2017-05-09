package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class SiteToCrawlFrequencyRepository extends RelationshipRepositoryBase<SiteDto, Long, CrawlFrequencyDto, Long> {

	protected SiteToCrawlFrequencyRepository() {
		super(SiteDto.class, CrawlFrequencyDto.class);
	}
}
