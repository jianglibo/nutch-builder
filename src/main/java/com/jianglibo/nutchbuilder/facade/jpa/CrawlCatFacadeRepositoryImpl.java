package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.facade.CrawlCatFacadeRepository;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class CrawlCatFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<CrawlCat> implements CrawlCatFacadeRepository {

	@Autowired
	public CrawlCatFacadeRepositoryImpl(CrawlCatRepository jpaRepo) {
		super(jpaRepo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CrawlCat findByName(String rn) {
		return null;
	}

}
