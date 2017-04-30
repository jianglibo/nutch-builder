package com.jianglibo.nutchbuilder.repository;

import com.jianglibo.nutchbuilder.domain.Role;

public interface RoleRepository extends RepositoryBase<Role> {
    Role findByName(String rn);

}
