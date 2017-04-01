package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface UserDtoRepository extends ResourceRepositoryV2<UserDto, Long> {

	public class UserDtoList extends ResourceListBase<UserDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public UserDtoList findAll(QuerySpec querySpec);
}

