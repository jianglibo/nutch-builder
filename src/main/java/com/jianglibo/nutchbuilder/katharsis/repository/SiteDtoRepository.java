package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface SiteDtoRepository extends ResourceRepositoryV2<SiteDto, Long> {


	public class SiteDtoList extends ResourceListBase<SiteDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public SiteDtoList findAll(QuerySpec querySpec);
}

