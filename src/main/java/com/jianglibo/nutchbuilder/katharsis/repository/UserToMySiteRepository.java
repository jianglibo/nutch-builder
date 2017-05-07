package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class UserToMySiteRepository extends RelationshipRepositoryBase<MySiteDto, Long, UserDto, Long> {

	protected UserToMySiteRepository() {
		super(MySiteDto.class, UserDto.class);
	}
}
