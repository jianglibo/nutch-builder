package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.Date;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiMetaInformation;

public abstract class DtoBase<T, E> implements Dto<T, E> {

	@JsonApiId
	private Long id;
	
	private Date createdAt;
	
//	@JsonApiMetaInformation
//	private String jwt;

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

//	public String getJwt() {
//		return jwt;
//	}
//
//	public void setJwt(String jwt) {
//		this.jwt = jwt;
//	}
}
