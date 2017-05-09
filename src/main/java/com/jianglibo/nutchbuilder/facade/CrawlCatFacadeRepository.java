package com.jianglibo.nutchbuilder.facade;

import com.jianglibo.nutchbuilder.domain.CrawlCat;

public interface CrawlCatFacadeRepository extends FacadeRepositoryBase<CrawlCat> {
	
	CrawlCat findByName(String rn);
	
	@Override
	CrawlCat save(CrawlCat entity);
	
	@Override
	void delete(Long id);
}
