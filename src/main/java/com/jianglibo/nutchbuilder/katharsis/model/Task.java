package com.jianglibo.nutchbuilder.katharsis.model;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;

@JsonApiResource(type = "tasks")
public class Task {
	   @JsonApiId
	    private Long id;

	    @JsonProperty("my-name")
	    private String name;
	    
	    @Size(max = 20, message="Description may not exceed {max} characters.")
	    private String description;

	    @JsonIgnore
	    private Long projectId;

	    @JsonApiToOne(opposite="tasks")
	    @JsonApiIncludeByDefault
	    private Project project;

	    public Task() {
	    }

	    public Task(Long id, String name) {
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

	    public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Project getProject() {
	        return project;
	    }

	    public void setProject(Project project) {
	        this.project = project;
	    }

	    public Long getProjectId() {
	        return projectId;
	    }

	    public void setProjectId(Long projectId) {
	        this.projectId = projectId;
	    }
}
