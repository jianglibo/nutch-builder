package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import com.jianglibo.nutchbuilder.domain.Site;

public interface SiteRepository extends RepositoryBase<Site> {

	Site findByDomainName(String homepage);
	
	List<Site> findAllByOrderByUpdatedAtDesc();
}
