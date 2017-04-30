package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class SiteFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Site, SiteRepository> implements SiteFacadeRepository {

	public SiteFacadeRepositoryImpl(SiteRepository jpaRepo) {
		super(jpaRepo);
	}
}
