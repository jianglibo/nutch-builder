package com.jianglibo.nutchbuilder.facade.jpa;

import java.util.List;

import com.jianglibo.nutchbuilder.facade.FacadeRepositoryBase;
import com.jianglibo.nutchbuilder.repository.RepositoryBase;

import io.katharsis.queryspec.QuerySpec;

public abstract class FacadeRepositoryBaseImpl<T, R extends RepositoryBase<T>> implements FacadeRepositoryBase<T> {

	private final R jpaRepo;
	
	public FacadeRepositoryBaseImpl(R jpaRepo) {
		this.jpaRepo = jpaRepo;
	}
	
	@Override
	public List<T> findAll(QuerySpec querySpec) {
		return jpaRepo.findAll(querySpec);
	}

	@Override
	public long count(QuerySpec querySpec) {
		return jpaRepo.count(querySpec);
	}

	@Override
	public T save(T entity) {
		return jpaRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		jpaRepo.delete(id);
	}

	@Override
	public T findOne(Long id) {
		return jpaRepo.findOne(id);
	}
	
	R getRepository() {
		return jpaRepo;
	}

}
