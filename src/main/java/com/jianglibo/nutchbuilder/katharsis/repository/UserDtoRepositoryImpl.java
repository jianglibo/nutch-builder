package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.userdetail.BootUserDetailManager;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.facade.BootUserFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto.OnCreateGroup;
import com.jianglibo.nutchbuilder.katharsis.repository.UserDtoRepository.UserDtoList;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil.RelationQuery;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

import io.katharsis.queryspec.QuerySpec;

@Component
public class UserDtoRepositoryImpl extends DtoRepositoryBase<UserDto, UserDtoList, BootUser, BootUserFacadeRepository> implements UserDtoRepository {
	
	private final BootUserDetailManager bootUserDetailManager;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
	public UserDtoRepositoryImpl(BootUserFacadeRepository bootUserRepository, BootUserDetailManager bootUserDetailManager) {
		super(UserDto.class, UserDtoList.class, BootUser.class, bootUserRepository);
		this.bootUserDetailManager = bootUserDetailManager;
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
		if ("password".equals(dto.getDtoApplyTo())) {
			validate(dto, OnCreateGroup.class, Default.class);
			getRepository().updatePassword(dto.getId(), passwordEncoder.encode(dto.getPassword()));
			dto.setPassword("");
			return dto;
		} else {
			validate(dto);
			BootUser entity = getRepository().findOne(dto.getId(), false);
			entity = getRepository().patch(entity, dto);
			return dto.fromEntity(entity);
		}
	}


	@Override
	protected UserDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}


	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}


	@Override
	protected UserDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
}
