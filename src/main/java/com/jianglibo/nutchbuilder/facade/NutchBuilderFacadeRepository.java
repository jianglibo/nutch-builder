package com.jianglibo.nutchbuilder.facade;


import com.jianglibo.nutchbuilder.domain.NutchBuilder;

public interface NutchBuilderFacadeRepository extends FacadeRepositoryBase<NutchBuilder> {
	NutchBuilder findByName(String rn);
}
