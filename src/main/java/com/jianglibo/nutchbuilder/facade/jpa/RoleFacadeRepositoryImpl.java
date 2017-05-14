package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
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
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public Role save(Role entity) {
		return super.save(entity);
	}
	

	@Override
	public Role findByName(String rn) {
		return getRepository().findByName(rn);
	}
}
