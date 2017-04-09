package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UrlFilterDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class SiteDtoToCrawlFrequencyRepository extends RelationshipRepositoryBase<SiteDto, Long, UrlFilterDto, Long> {

	protected SiteDtoToCrawlFrequencyRepository() {
		super(SiteDto.class, UrlFilterDto.class);
	}
}
