package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SimplePageable;
import com.jianglibo.nutchbuilder.repository.RoleRepository;

import io.katharsis.queryspec.FilterSpec;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.queryspec.SortSpec;
import io.katharsis.repository.ResourceRepositoryBase;

@Component
public class RoleDtoRepositoryImpl  extends ResourceRepositoryBase<RoleDto, Long> implements RoleDtoRepository {
	
	private final RoleRepository roleRepository;

	@Autowired
	public RoleDtoRepositoryImpl(RoleRepository roleRepository) {
		super(RoleDto.class);
		this.roleRepository = roleRepository;
	}

	@Override
	public void delete(Long id) {
		roleRepository.delete(id);
	}

	@Override
	public <S extends RoleDto> S save(S roleDto) {
		Role role;
		if (roleDto.getId() == null) {
			role = roleRepository.findOne(roleDto.getId());
		} else {
			role = new Role();
		}
		role = roleDto.patch(role);
		return (S) roleDto.fromEntity(roleRepository.save(role));
	}

	@Override
	public RoleDtoList findAll(QuerySpec querySpec) {
		Example<Role> example = Example.of(new Role());
		Long count = roleRepository.count(example);
		
		List<Role> roles = roleRepository.findAll(example, new SimplePageable(querySpec, count)).getContent();
		Long limit = querySpec.getLimit();
		Long offset = querySpec.getOffset();
		List<SortSpec> sorts = querySpec.getSort();
		List<FilterSpec> filters = querySpec.getFilters();
		List<RoleDto> list = new RoleDto().batchConvert(roles);
		RoleDtoList listOb = new RoleDtoList();
		listOb.setMeta(new DtoListMeta(count));
		listOb.setLinks(new DtoListLinks());
		listOb.addAll(list);
		return listOb;
	}

}
