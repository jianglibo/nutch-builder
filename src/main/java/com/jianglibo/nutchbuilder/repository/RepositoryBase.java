package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;

import io.katharsis.queryspec.QuerySpec;

@NoRepositoryBean
public interface RepositoryBase<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
	
	List<T> findAll(QuerySpec querySpec);
	
	long count(QuerySpec querySpec);
	
	@Override
	@Transactional
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	void delete(T entity);
	
	// it's important
	@Transactional
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	void delete(Long id);
	
	@Transactional
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	void delete(Iterable<? extends T> entities);
	
	@Override
	@Transactional
	@PreAuthorize(PreAuthorizeExpression.IMPOSSIBLE)
	void deleteAll();

}
