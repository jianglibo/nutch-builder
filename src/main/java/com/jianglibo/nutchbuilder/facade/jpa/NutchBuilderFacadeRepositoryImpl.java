package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.NutchBuilder;
import com.jianglibo.nutchbuilder.facade.NutchBuilderFacadeRepository;
import com.jianglibo.nutchbuilder.repository.NutchBuilderRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class NutchBuilderFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<NutchBuilder, NutchBuilderRepository> implements NutchBuilderFacadeRepository {

	public NutchBuilderFacadeRepositoryImpl(NutchBuilderRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public NutchBuilder findByName(String rn) {
		return getRepository().findByName(rn);
	}
}
