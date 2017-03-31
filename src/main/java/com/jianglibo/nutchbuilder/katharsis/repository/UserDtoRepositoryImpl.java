package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.katharsis.dto.Dto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;

import io.katharsis.queryspec.FilterOperator;
import io.katharsis.queryspec.FilterSpec;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.queryspec.SortSpec;
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
		for(FilterSpec spec: querySpec.getFilters()) {
			if ("id".equals(spec.getAttributePath().get(0)) && (spec.getOperator() == FilterOperator.EQ)) {
				Object v = spec.getValue();
				List<BootUser> bus = new ArrayList<>();
				if (v instanceof Long) {
					bus.add(bootUserRepository.findOne((Long)v));
				} else {
					List<Long> ids = (List<Long>) v;
					for(Long lid : ids) {
						bus.add(bootUserRepository.findOne(lid));
					}
					
				}
				UserDtoList udl = new UserDtoList();
				udl.addAll(Dto.convertToDto(UserDto.class, bus));
				return udl;
			}
		}
		BootUser bu = new BootUser();
		bu.setAccountNonExpired(true);
		bu.setAccountNonLocked(true);
		Example<BootUser> example = Example.of(bu);
		Long count = bootUserRepository.count(example);
		
		List<BootUser> users = bootUserRepository.findAll(querySpec);
		
		List<UserDto> list = Dto.convertToDto(UserDto.class, users);
		UserDtoList listOb = new UserDtoList();
		listOb.setMeta(new DtoListMeta(count));
		listOb.setLinks(new DtoListLinks());
		listOb.addAll(list);
		return listOb;

	}

}
