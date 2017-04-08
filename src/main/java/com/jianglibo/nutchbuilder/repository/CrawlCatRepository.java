package com.jianglibo.nutchbuilder.repository;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.domain.CrawlCat;

public interface CrawlCatRepository extends RepositoryBase<CrawlCat> {
	CrawlCat findByName(String rn);
	
	@Override
	@PreAuthorize("hasRole('ADMINSTRATOR')")
	<S extends CrawlCat> S save(S entity);
}
