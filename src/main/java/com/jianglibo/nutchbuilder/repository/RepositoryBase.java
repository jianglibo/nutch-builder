package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import io.katharsis.queryspec.QuerySpec;

@NoRepositoryBean
public interface RepositoryBase<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
	
	List<T> findAll(QuerySpec querySpec);
	
	long count(QuerySpec querySpec);
}
