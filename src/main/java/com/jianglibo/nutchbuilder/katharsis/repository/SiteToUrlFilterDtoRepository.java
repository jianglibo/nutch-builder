package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class SiteToUrlFilterDtoRepository extends RelationshipRepositoryBase<SiteDto, Long, CrawlFrequencyDto, Long> {

	protected SiteToUrlFilterDtoRepository() {
		super(SiteDto.class, CrawlFrequencyDto.class);
	}
}
