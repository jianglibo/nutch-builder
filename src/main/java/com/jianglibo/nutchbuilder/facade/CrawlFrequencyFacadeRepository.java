package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.CrawlFrequency;

public interface CrawlFrequencyFacadeRepository extends FacadeRepositoryBase<CrawlFrequency> {
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE + " or (#entity.site.creator.id == principal.id)")
	CrawlFrequency save(@P("entity") CrawlFrequency entity);
}
