package com.jianglibo.nutchbuilder.katharsis.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;

@JsonApiResource(type = "projects")
public class Project {
	@JsonApiId
	private Long id;

	@JsonProperty
	private String name;

	@JsonApiToMany(opposite = "project")
	private List<Task> tasks = new ArrayList<>();

	public Project() {
	}

	public Project(Long id) {
		this.id = id;
	}

	public Project(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
