package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class SiteToUserRepository extends RelationshipRepositoryBase<SiteDto, Long, UserDto, Long> {

	protected SiteToUserRepository() {
		super(SiteDto.class, UserDto.class);
	}
}
