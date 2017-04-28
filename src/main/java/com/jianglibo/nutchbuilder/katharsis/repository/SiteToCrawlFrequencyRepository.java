package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UrlFilterDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class SiteToCrawlFrequencyRepository extends RelationshipRepositoryBase<SiteDto, Long, UrlFilterDto, Long> {

	protected SiteToCrawlFrequencyRepository() {
		super(SiteDto.class, UrlFilterDto.class);
	}
}
