package com.jianglibo.nutchbuilder.katharsis.dto;

import io.katharsis.resource.annotations.JsonApiId;

public abstract class DtoBase<T, E> implements Dto<T, E> {

	@JsonApiId
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
