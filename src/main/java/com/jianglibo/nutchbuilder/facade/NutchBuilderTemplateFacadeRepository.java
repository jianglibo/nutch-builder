package com.jianglibo.nutchbuilder.facade;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;


public interface NutchBuilderTemplateFacadeRepository extends FacadeRepositoryBase<NutchBuilderTemplate> {
	NutchBuilderTemplate findByName(String rn);
}
