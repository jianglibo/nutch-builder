package com.jianglibo.nutchbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jianglibo.nutchbuilder.domain.NutchBuilder;

@RepositoryRestResource //(collectionResourceRel = "nbuilder", path = "nbuilder")
public interface NutchBuilderRepository extends JpaRepository<NutchBuilder, Long>, NutchBuilderRepositoryCustom, JpaSpecificationExecutor<NutchBuilder> {
	NutchBuilder findByName(String rn);
}
