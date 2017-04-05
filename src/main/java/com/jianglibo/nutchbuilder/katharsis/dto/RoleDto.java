package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.Role;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.ROLE)
@DtoToEntity(entityClass=Role.class)
public class RoleDto implements Dto<RoleDto, Role>{

	@JsonApiId
	private Long id;
	
	@NotNull
	@Size(min=3, max=30)
	private String name;
	
	private Date createdAt;
	
	@Override
	public RoleDto fromEntity(Role role) {
		setId(role.getId());
		setName(role.getName());
		setCreatedAt(role.getCreatedAt());
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@Override
	public String toString() {
		return String.format("[%s,%s]", getId(), getName());
	}
}
