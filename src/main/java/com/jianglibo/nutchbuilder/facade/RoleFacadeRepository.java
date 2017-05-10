package com.jianglibo.nutchbuilder.facade;

import com.jianglibo.nutchbuilder.domain.Role;


public interface RoleFacadeRepository extends FacadeRepositoryBase<Role> {
    Role findByName(String rn);
}
