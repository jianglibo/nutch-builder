package com.jianglibo.nutchbuilder.facade;

import java.util.List;

public interface FacadeRepositoryBase<T> {
	
	List<T> findRange(long offset, long limit, SortBroker...sortFields);
	
	long count();
	
	T save(T entity);
	
	void delete(Long id);

	T findOne(Long id);
}
