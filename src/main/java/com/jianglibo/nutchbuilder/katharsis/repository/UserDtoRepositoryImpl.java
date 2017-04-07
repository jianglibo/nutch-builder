package com.jianglibo.nutchbuilder.katharsis.repository;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.userdetail.BootUserDetailManager;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto.OnCreateGroup;
import com.jianglibo.nutchbuilder.katharsis.repository.UserDtoRepository.UserDtoList;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

@Component
public class UserDtoRepositoryImpl extends DtoRepositoryBase<UserDto, UserDtoList, BootUser> implements UserDtoRepository {
	
	private final BootUserRepository repository;
	
	private final BootUserDetailManager bootUserDetailManager;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	public UserDtoRepositoryImpl(BootUserRepository bootUserRepository, BootUserDetailManager bootUserDetailManager) {
		super(UserDto.class, UserDtoList.class, BootUser.class, bootUserRepository);
		this.repository = bootUserRepository;
		this.bootUserDetailManager = bootUserDetailManager;
	}
	
	@Override
	public <S extends UserDto> S createNew(S dto) {
		validate(dto, OnCreateGroup.class, Default.class);
		BootUserPrincipal bu = new BootUserPrincipal(dto);
		return (S) dto.fromEntity(bootUserDetailManager.createUserAndReturn(bu));
	}
	
	@Override
	public <S extends UserDto> S modify(S dto) {
		if (dto.isUpdatePassword()) {
			validate(dto, OnCreateGroup.class, Default.class);
			return (S) updatePassword(dto);
		} else {
			validate(dto);
			BootUser entity = repository.findOne(dto.getId());
			entity = dto.patch(entity);
			return (S) dto.fromEntity(repository.save(entity));
		}
	}

	private  UserDto updatePassword(UserDto dto) {
		BootUser entity = repository.findOne(dto.getId());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		repository.save(entity);
		dto.setPassword("");
		return dto;
	}
}
