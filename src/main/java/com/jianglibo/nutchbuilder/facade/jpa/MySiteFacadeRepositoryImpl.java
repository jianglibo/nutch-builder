package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.MySite;
import com.jianglibo.nutchbuilder.facade.MySiteFacadeRepository;
import com.jianglibo.nutchbuilder.repository.MySiteRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class MySiteFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<MySite, MySiteRepository> implements MySiteFacadeRepository {

	public MySiteFacadeRepositoryImpl(MySiteRepository jpaRepo) {
		super(jpaRepo);
	}
}
