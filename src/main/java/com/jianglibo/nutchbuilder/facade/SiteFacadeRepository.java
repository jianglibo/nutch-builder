package com.jianglibo.nutchbuilder.facade;

import com.jianglibo.nutchbuilder.domain.Site;

public interface SiteFacadeRepository extends FacadeRepositoryBase<Site> {
	Site findByDomainName(String homepage);
}
