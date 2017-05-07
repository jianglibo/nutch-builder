package com.jianglibo.nutchbuilder.katharsis.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.Role;

import io.katharsis.resource.annotations.JsonApiMetaInformation;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.ROLE)
@DtoToEntity(entityClass=Role.class)
public class RoleDto extends DtoBase<RoleDto, Role>{
	
	@NotNull
	@Size(min=3, max=30)
	private String name;
	
	@JsonApiMetaInformation
	private String jwt;
	
	@Override
	public RoleDto fromEntity(Role role) {
		setId(role.getId());
		setName(role.getName());
		setCreatedAt(role.getCreatedAt());
		return this;
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
	
	

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return String.format("[%s,%s]", getId(), getName());
	}
}
