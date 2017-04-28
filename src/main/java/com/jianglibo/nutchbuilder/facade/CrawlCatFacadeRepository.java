package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.domain.CrawlCat;

public interface CrawlCatFacadeRepository extends FacadeRepositoryBase<CrawlCat> {
	
	CrawlCat findByName(String rn);
	
	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	CrawlCat save(CrawlCat entity);
}
