package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.stream.Collectors;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.userdetail.BootUserDetailManager;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.facade.BootUserFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto.OnCreateGroup;
import com.jianglibo.nutchbuilder.katharsis.repository.UserDtoRepository.UserDtoList;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

@Component
public class UserDtoRepositoryImpl extends DtoRepositoryBase<UserDto, UserDtoList, BootUser> implements UserDtoRepository {
	
	private final BootUserDetailManager bootUserDetailManager;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
	public UserDtoRepositoryImpl(BootUserFacadeRepository bootUserRepository, BootUserDetailManager bootUserDetailManager) {
		super(UserDto.class, UserDtoList.class, BootUser.class, bootUserRepository);
		this.bootUserDetailManager = bootUserDetailManager;
	}
    
    @Override
    public <S extends UserDto> S create(S resource) {
    	return super.create(resource);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @PreAuthorize("hasRole('KKKKKKK')")
    public UserDto save(UserDto dto) {
    	return super.save(dto);
    }
    
    @Override
    public UserDto convertToDto(BootUser entity) {
    	UserDto user = super.convertToDto(entity);
    	user.setRoles(entity.getRoles().stream().map(r -> new RoleDto().fromEntity(r)).collect(Collectors.toList()));
    	return user;
    }
	
	@Override
	public UserDto createNew(UserDto dto) {
		validate(dto, OnCreateGroup.class, Default.class);
		BootUserPrincipal bu = new BootUserPrincipal(dto);
		return dto.fromEntity(bootUserDetailManager.createUserAndReturn(bu));
	}
	
	@Override
	public UserDto modify(UserDto dto) {
		if (dto.isUpdatePassword()) {
			validate(dto, OnCreateGroup.class, Default.class);
			return updatePassword(dto);
		} else {
			validate(dto);
			BootUser entity = getRepository().findOne(dto.getId());
			entity = dto.patch(entity);
			return dto.fromEntity(getRepository().save(entity));
		}
	}

	private  UserDto updatePassword(UserDto dto) {
		BootUser entity = getRepository().findOne(dto.getId());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		getRepository().save(entity);
		dto.setPassword("");
		return dto;
	}
}
