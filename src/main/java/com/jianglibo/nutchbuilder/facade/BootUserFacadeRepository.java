package com.jianglibo.nutchbuilder.facade;

import javax.transaction.Transactional;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.domain.BootUser;

public interface BootUserFacadeRepository extends FacadeRepositoryBase<BootUser> {
    
    @Transactional
    BootUser save(BootUser entity);
    
    @Override
    //cannot delete yourself.
    @PreAuthorize("hasRole('ADMINISTRATOR') and (#id != principal.id)")
    @Transactional
    public void delete(@P("id") Long id);
}
