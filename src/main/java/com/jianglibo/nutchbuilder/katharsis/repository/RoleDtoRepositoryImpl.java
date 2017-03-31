package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.katharsis.dto.Dto;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.repository.RoleRepository;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;

@Component
public class RoleDtoRepositoryImpl  extends ResourceRepositoryBase<RoleDto, Long> implements RoleDtoRepository {
	
	private final RoleRepository roleRepository;

	@Autowired
	public RoleDtoRepositoryImpl(RoleRepository roleRepository) {
		super(RoleDto.class);
		this.roleRepository = roleRepository;
	}
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles/32768 -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Delete
	@Override
	public void delete(Long id) {
		roleRepository.delete(id);
	}

//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"name": "test"}, "type": "roles"}}' -Method Post
	@Override
	public <S extends RoleDto> S save(S roleDto) {
		Role role;
		if (roleDto.getId() != null) {
			role = roleRepository.findOne(roleDto.getId());
		} else {
			role = new Role();
		}
		role = roleDto.patch(role);
		return (S) roleDto.fromEntity(roleRepository.save(role));
	}

	@Override
	public RoleDtoList findAll(QuerySpec querySpec) {
		Long count = roleRepository.count(querySpec);
		List<Role> roles = roleRepository.findAll(querySpec);
		List<RoleDto> list = Dto.convertToDto(RoleDto.class, roles);
		RoleDtoList listOb = new RoleDtoList();
		listOb.setMeta(new DtoListMeta(count));
		listOb.setLinks(new DtoListLinks());
		listOb.addAll(list);
		return listOb;
	}

}
