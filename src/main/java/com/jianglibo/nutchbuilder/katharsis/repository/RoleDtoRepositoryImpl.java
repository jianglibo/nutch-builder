package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.repository.RoleDtoRepository.RoleDtoList;
import com.jianglibo.nutchbuilder.repository.RoleRepository;

@Component
public class RoleDtoRepositoryImpl  extends DtoRepositoryBase<RoleDto, RoleDtoList, Role> implements RoleDtoRepository {
	
	private final RoleRepository roleRepository;

	@Autowired
	public RoleDtoRepositoryImpl(RoleRepository roleRepository) {
		super(RoleDto.class, RoleDtoList.class, Role.class, roleRepository);
		this.roleRepository = roleRepository;
	}
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles/32768 -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Delete
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"name": "test"}, "type": "roles"}}' -Method Post
}
