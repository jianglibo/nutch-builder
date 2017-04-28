package com.jianglibo.nutchbuilder.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.domain.BootUser;


public interface BootUserRepository extends RepositoryBase<BootUser> {

    BootUser findByEmail(@Param("email") String email);

    BootUser findByMobile(@Param("mobile") String mobile);

    BootUser findByName(@Param("name") String name);
    
    @Override
    @Transactional
    public <S extends BootUser> S save(S entity);
    
    @Override
    //cannot delete yourself.
    @PreAuthorize("hasRole('ADMINISTRATOR') and (#e.id != principal.id)")
    @Transactional
    public void delete(@P("e") BootUser entity);
    
    @Override
    //cannot delete yourself.
    @PreAuthorize("hasRole('ADMINISTRATOR') and (#id != principal.id)")
    @Transactional
    public void delete(@P("id") Long id);
}
