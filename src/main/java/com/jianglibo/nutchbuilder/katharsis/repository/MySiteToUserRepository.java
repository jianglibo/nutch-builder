package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.alter.RelationshipRepositoryBaseMy;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;


@Component
public class MySiteToUserRepository extends RelationshipRepositoryBaseMy<MySiteDto, UserDto> {

	protected MySiteToUserRepository() {
		super( MySiteDto.class, UserDto.class);
	}
}
