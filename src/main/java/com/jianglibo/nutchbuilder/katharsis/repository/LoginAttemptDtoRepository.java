package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.dto.LoginAttemptDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface LoginAttemptDtoRepository extends ResourceRepositoryV2<LoginAttemptDto, Long> {


	public class LoginAttemptDtoList extends ResourceListBase<LoginAttemptDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public LoginAttemptDtoList findAll(QuerySpec querySpec);
}

