package com.jianglibo.nutchbuilder.repository;

import com.jianglibo.nutchbuilder.domain.NutchBuilder;

public interface NutchBuilderRepository extends RepositoryBase<NutchBuilder> {
	NutchBuilder findByName(String rn);
}
