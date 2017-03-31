package com.jianglibo.nutchbuilder.repository;

//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;

//@RepositoryRestResource //(collectionResourceRel = "nbuildertpl", path = "nbuildertpl")
public interface NutchBuilderTemplateRepository extends RepositoryBase<NutchBuilderTemplate> {
	NutchBuilderTemplate findByName(String rn);
}
