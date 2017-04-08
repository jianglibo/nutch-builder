package com.jianglibo.nutchbuilder.katharsis.repository;


import com.jianglibo.nutchbuilder.katharsis.dto.CrawlFrequencyDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface CrawlFrequencyDtoRepository extends ResourceRepositoryV2<CrawlFrequencyDto, Long> {


	public class CrawlFrequencyDtoList extends ResourceListBase<CrawlFrequencyDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public CrawlFrequencyDtoList findAll(QuerySpec querySpec);
}

