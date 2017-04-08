package com.jianglibo.nutchbuilder.katharsis.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlCat;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.CRAWL_CAT)
@DtoToEntity(entityClass=CrawlCat.class)
public class CrawlCatDto extends DtoBase<CrawlCatDto, CrawlCat>{
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String projectRoot;
	
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectRoot() {
		return projectRoot;
	}

	public void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public CrawlCatDto fromEntity(CrawlCat entity) {
		setDescription(entity.getDescription());
		setId(entity.getId());
		setName(entity.getName());
		setCreatedAt(entity.getCreatedAt());
		setProjectRoot(entity.getProjectRoot());
		return this;
	}

	@Override
	public CrawlCat patch(CrawlCat entity) {
		entity.setDescription(getDescription());
		entity.setName(getName());
		entity.setProjectRoot(getProjectRoot());
		return entity;
	}

}
