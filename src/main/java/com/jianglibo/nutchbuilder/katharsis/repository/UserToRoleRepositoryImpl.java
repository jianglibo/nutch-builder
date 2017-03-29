package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class UserToRoleRepositoryImpl extends RelationshipRepositoryBase<UserDto, Long, RoleDto, Long> {

    protected UserToRoleRepositoryImpl() {
		super(UserDto.class, RoleDto.class);
	}
}
