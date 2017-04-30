package com.jianglibo.nutchbuilder.repository;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;

public interface NutchBuilderTemplateRepository extends RepositoryBase<NutchBuilderTemplate> {
	NutchBuilderTemplate findByName(String rn);
}
