package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.Date;

import io.katharsis.resource.annotations.JsonApiId;

public abstract class DtoBase<T, E> implements Dto<T, E> {

	@JsonApiId
	private Long id;
	
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
