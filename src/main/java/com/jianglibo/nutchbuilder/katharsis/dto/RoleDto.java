package com.jianglibo.nutchbuilder.katharsis.dto;

import com.jianglibo.nutchbuilder.domain.Role;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = "roles")
public class RoleDto implements Dto<RoleDto, Role>{

	@JsonApiId
	private Long id;
	
	private String name;
	
	@Override
	public RoleDto fromEntity(Role role) {
		setId(role.getId());
		setName(role.getName());
		return this;
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

	@Override
	public Role patch(Role entity) {
		entity.setName(getName());
		return entity;
	}
}
