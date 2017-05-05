package com.jianglibo.nutchbuilder.katharsis.repository;


import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface MySiteDtoRepository extends ResourceRepositoryV2<MySiteDto, Long> {


	public class MySiteDtoList extends ResourceListBase<MySiteDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public MySiteDtoList findAll(QuerySpec querySpec);
}

