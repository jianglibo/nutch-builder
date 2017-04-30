package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.Role;


public interface RoleFacadeRepository extends FacadeRepositoryBase<Role> {
    Role findByName(String rn);

    @Override
    @PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
    Role save(Role entity);
    
    @Override
    @PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
    void delete(Long id);
}
