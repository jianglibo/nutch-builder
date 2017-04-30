package com.jianglibo.nutchbuilder.repository;

import com.jianglibo.nutchbuilder.domain.CrawlCat;

public interface CrawlCatRepository extends RepositoryBase<CrawlCat> {
	CrawlCat findByName(String rn);
}
