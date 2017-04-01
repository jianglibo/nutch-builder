package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.repository.UserDtoRepository.UserDtoList;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;

@Component
public class UserDtoRepositoryImpl  extends DtoRepositoryBase<UserDto, UserDtoList, BootUser> implements UserDtoRepository {
	
	private final BootUserRepository bootUserRepository;

	public UserDtoRepositoryImpl(BootUserRepository bootUserRepository) {
		super(UserDto.class, UserDtoList.class, BootUser.class, bootUserRepository);
		this.bootUserRepository = bootUserRepository;
	}
}
