package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.model.Project;
import com.jianglibo.nutchbuilder.katharsis.model.Task;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class TaskToProjectRepositoryImpl extends RelationshipRepositoryBase<Task, Long, Project, Long> {

	public TaskToProjectRepositoryImpl() {
		super(Task.class, Project.class);
	}
}
