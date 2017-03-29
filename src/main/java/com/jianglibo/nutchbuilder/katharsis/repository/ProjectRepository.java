package com.jianglibo.nutchbuilder.katharsis.repository;

import com.jianglibo.nutchbuilder.katharsis.model.Project;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface ProjectRepository extends ResourceRepositoryV2<Project, Long> {

	public class ProjectList extends ResourceListBase<Project, DtoListMeta, DtoListLinks> {

	}

	@Override
	public ProjectList findAll(QuerySpec querySpec);
}

