package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.UrlFilter;
import com.jianglibo.nutchbuilder.facade.UrlFilterFacadeRepository;
import com.jianglibo.nutchbuilder.repository.UrlFilterRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class UrlFilterFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<UrlFilter> implements UrlFilterFacadeRepository {

	public UrlFilterFacadeRepositoryImpl(UrlFilterRepository jpaRepo) {
		super(jpaRepo);
	}


}
