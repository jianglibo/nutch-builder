package com.jianglibo.nutchbuilder.repository;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.domain.Site;

public interface SiteRepository extends RepositoryBase<Site> {

//	@PreAuthorize("hasRole('ADMINISTRATOR') or (#entity.id == principal.id)")
	@PreAuthorize("isFullyAuthenticated()")
	<S extends Site> S save(@P("entity")S entity);
	
}
