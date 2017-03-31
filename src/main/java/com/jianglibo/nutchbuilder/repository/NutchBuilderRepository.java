package com.jianglibo.nutchbuilder.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jianglibo.nutchbuilder.domain.NutchBuilder;

@RepositoryRestResource //(collectionResourceRel = "nbuilder", path = "nbuilder")
public interface NutchBuilderRepository extends RepositoryBase<NutchBuilder> {
	NutchBuilder findByName(String rn);
}
