package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.model.Project;
import com.jianglibo.nutchbuilder.katharsis.model.Task;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class ProjectToTaskRepositoryImpl extends RelationshipRepositoryBase<Project, Long, Task, Long> {

    protected ProjectToTaskRepositoryImpl() {
		super(Project.class, Task.class);
	}
}
