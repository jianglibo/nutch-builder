package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.UrlFilterDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface UrlFilterDtoRepository extends ResourceRepositoryV2<UrlFilterDto, Long> {


	public class UrlFilterDtoList extends ResourceListBase<UrlFilterDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	UrlFilterDtoList findAll(QuerySpec querySpec);
	
	
}

