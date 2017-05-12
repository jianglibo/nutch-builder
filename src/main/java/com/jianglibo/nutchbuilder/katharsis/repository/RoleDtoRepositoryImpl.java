package com.jianglibo.nutchbuilder.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.facade.RoleFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.repository.RoleDtoRepository.RoleDtoList;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class RoleDtoRepositoryImpl  extends DtoRepositoryBase<RoleDto, RoleDtoList, Role, RoleFacadeRepository> implements RoleDtoRepository {
	
	@Autowired
	public RoleDtoRepositoryImpl(RoleFacadeRepository repository) {
		super(RoleDto.class, RoleDtoList.class, Role.class, repository);
	}
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles/32768 -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Delete
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"name": "test"}, "type": "roles"}}' -Method Post

	@Override
	protected RoleDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RoleDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}
}
