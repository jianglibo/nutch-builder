package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.katharsis.model.Project;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;

@Component
public class ProjectRepositoryImpl  extends ResourceRepositoryBase<Project, Long> implements ProjectRepository {

	private static final AtomicLong ID_GENERATOR = new AtomicLong(124);

	private Map<Long, Project> projects = new HashMap<>();

	public ProjectRepositoryImpl() {
		super(Project.class);
		List<String> interests = new ArrayList<>();
		interests.add("coding");
		interests.add("art");
		save(new Project(123L, "Great Project"));
	}

	@Override
	public synchronized void delete(Long id) {
		projects.remove(id);
	}

	@Override
	public synchronized <S extends Project> S save(S project) {
		if (project.getId() == null) {
			project.setId(ID_GENERATOR.getAndIncrement());
		}
		projects.put(project.getId(), project);
		return project;
	}

	@Override
	public synchronized ProjectList findAll(QuerySpec querySpec) {
		ProjectList list = new ProjectList();
		list.addAll(projects.values());
		list.setMeta(new ProjectListMeta((long)list.size()));
		list.setLinks(new ProjectListLinks());
		querySpec.apply(projects.values(), list);
		return list;
	}

}
