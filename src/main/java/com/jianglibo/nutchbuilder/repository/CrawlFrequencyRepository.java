package com.jianglibo.nutchbuilder.repository;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.CrawlFrequency;

public interface CrawlFrequencyRepository extends RepositoryBase<CrawlFrequency> {
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE + " or (#entity.site.creator.id == principal.id)")
	<S extends CrawlFrequency> S save(@P("entity") S entity);
}
