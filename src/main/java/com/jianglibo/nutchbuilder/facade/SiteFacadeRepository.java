package com.jianglibo.nutchbuilder.facade;

import java.util.List;

import com.jianglibo.nutchbuilder.domain.Site;

public interface SiteFacadeRepository extends FacadeRepositoryBase<Site> {
	Site findByDomainName(String homepage);
	
	List<Site> findByCrawlCat(Long crawlCatId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByCrawlCat(Long crawlCatId);
}
