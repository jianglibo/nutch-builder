package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.CrawlCat;

public interface CrawlCatFacadeRepository extends FacadeRepositoryBase<CrawlCat> {
	
	CrawlCat findByName(String rn);
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	CrawlCat save(CrawlCat entity);
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	void delete(Long id);
}
