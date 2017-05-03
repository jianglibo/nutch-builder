package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.facade.RoleFacadeRepository;
import com.jianglibo.nutchbuilder.repository.RoleRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class RoleFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Role, RoleRepository> implements RoleFacadeRepository {
	
	public RoleFacadeRepositoryImpl(RoleRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public Role findByName(String rn) {
		return getRepository().findByName(rn);
	}
}
