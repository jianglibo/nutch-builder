package com.jianglibo.nutchbuilder.facade;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;

import io.katharsis.queryspec.QuerySpec;

public interface FacadeRepositoryBase<T> {

	List<T> findAll(QuerySpec querySpec);
	
	long count(QuerySpec querySpec);
	
	@Transactional
	@PreAuthorize(PreAuthorizeExpression.IMPOSSIBLE)
	T save(T entity);
	
	// it's important
	@Transactional
	@PreAuthorize(PreAuthorizeExpression.IMPOSSIBLE)
	void delete(Long id);
	
	@Transactional
	@PreAuthorize(PreAuthorizeExpression.IMPOSSIBLE)
	void deleteAll();

	T findOne(Long id);
}
