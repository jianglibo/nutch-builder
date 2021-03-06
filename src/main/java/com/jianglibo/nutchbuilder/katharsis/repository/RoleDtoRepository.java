package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface RoleDtoRepository extends ResourceRepositoryV2<RoleDto, Long> {


	public class RoleDtoList extends ResourceListBase<RoleDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public RoleDtoList findAll(QuerySpec querySpec);
}

