package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.UrlFilter;

public interface UrlFilterFacadeRepository extends FacadeRepositoryBase<UrlFilter> {
    
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE + " or (#entity.site.creator.id == principal.id)")
	UrlFilter save(@P("entity") UrlFilter entity);
}
