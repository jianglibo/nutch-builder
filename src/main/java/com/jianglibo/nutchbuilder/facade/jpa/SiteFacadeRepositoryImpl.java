package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepositoryImpl;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class SiteFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Site> implements SiteFacadeRepository {

	public SiteFacadeRepositoryImpl(SiteRepositoryImpl jpaRepo) {
		super(jpaRepo);
	}


}
