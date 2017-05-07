package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class MySiteToUserRepository extends RelationshipRepositoryBase<UserDto, Long, MySiteDto, Long> {

	protected MySiteToUserRepository() {
		super( UserDto.class, MySiteDto.class);
	}
}
