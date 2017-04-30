package com.jianglibo.nutchbuilder.facade;

import java.util.List;

import io.katharsis.queryspec.QuerySpec;

public interface FacadeRepositoryBase<T> {

	List<T> findAll(QuerySpec querySpec);
	
	long count(QuerySpec querySpec);
	
	T save(T entity);
	
	void delete(Long id);

	T findOne(Long id);
}
