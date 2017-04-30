package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;
import com.jianglibo.nutchbuilder.facade.NutchBuilderTemplateFacadeRepository;
import com.jianglibo.nutchbuilder.repository.NutchBuilderTemplateRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class NutchBuilderTemplateFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<NutchBuilderTemplate, NutchBuilderTemplateRepository> implements NutchBuilderTemplateFacadeRepository {

	public NutchBuilderTemplateFacadeRepositoryImpl(NutchBuilderTemplateRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public NutchBuilderTemplate findByName(String rn) {
		return getRepository().findByName(rn);
	}


}
