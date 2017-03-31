package com.jianglibo.nutchbuilder.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jianglibo.nutchbuilder.domain.Role;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends RepositoryBase<Role> {
    Role findByName(String rn);
}
