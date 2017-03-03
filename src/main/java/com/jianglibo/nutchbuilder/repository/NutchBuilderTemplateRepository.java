package com.jianglibo.nutchbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;

@RepositoryRestResource //(collectionResourceRel = "nbuildertpl", path = "nbuildertpl")
public interface NutchBuilderTemplateRepository extends JpaRepository<NutchBuilderTemplate, Long>, NutchBuilderTemplateRepositoryCustom, JpaSpecificationExecutor<NutchBuilderTemplate> {
	NutchBuilderTemplate findByName(String rn);
}
