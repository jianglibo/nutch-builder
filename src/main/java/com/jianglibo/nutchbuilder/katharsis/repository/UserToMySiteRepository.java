package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.alter.RelationshipRepositoryBaseMy;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

@Component
public class UserToMySiteRepository extends RelationshipRepositoryBaseMy<UserDto, MySiteDto> {

	protected UserToMySiteRepository() {
		super(UserDto.class, MySiteDto.class);
	}
}
