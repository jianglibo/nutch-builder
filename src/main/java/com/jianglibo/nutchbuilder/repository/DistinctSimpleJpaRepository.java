package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class DistinctSimpleJpaRepository<T> extends SimpleJpaRepository<T, Long>{
	
	private EntityManager em;

	public DistinctSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.em = em;
	}

}
