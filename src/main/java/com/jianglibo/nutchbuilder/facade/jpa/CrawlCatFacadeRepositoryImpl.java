package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.facade.CrawlCatFacadeRepository;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class CrawlCatFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<CrawlCat, CrawlCatRepository> implements CrawlCatFacadeRepository {

	@Autowired
	public CrawlCatFacadeRepositoryImpl(CrawlCatRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public CrawlCat findByName(String rn) {
		return getRepository().findByName(rn);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public CrawlCat save(CrawlCat entity) {
		return super.save(entity);
	}

}
