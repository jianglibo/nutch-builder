package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;

@Component
public class UserDtoRepositoryImpl  extends ResourceRepositoryBase<UserDto, Long> implements UserDtoRepository {
	
	private final BootUserRepository bootUserRepository;

	public UserDtoRepositoryImpl(BootUserRepository bootUserRepository) {
		super(UserDto.class);
		this.bootUserRepository = bootUserRepository;
	}

//	@Override
//	public void delete(Long id) {
//		projects.remove(id);
//	}

//	@Override
//	public <S extends BootUser> S save(S project) {
//		if (project.getId() == null) {
//			project.setId(ID_GENERATOR.getAndIncrement());
//		}
//		projects.put(project.getId(), project);
//		return project;
//	}

	@Override
	public UserDtoList findAll(QuerySpec querySpec) {
		List<UserDto> list = bootUserRepository.findAll().stream().map(entity -> new UserDto().fromEntity(entity)).collect(Collectors.toList());
		UserDtoList listOb = new UserDtoList();
		listOb.setMeta(new BootUserListMeta());
		listOb.setLinks(new UserDtoListLinks());
		querySpec.apply(list, listOb);
		return listOb;
	}

}
