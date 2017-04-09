package com.jianglibo.nutchbuilder.repository;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.domain.UrlFilter;

public interface UrlFilterRepository extends RepositoryBase<UrlFilter> {
    
	@Override
	@PreAuthorize("hasRole('USER') or (#entity.site.id == principal.id)")
	<S extends UrlFilter> S save(S entity);
}
