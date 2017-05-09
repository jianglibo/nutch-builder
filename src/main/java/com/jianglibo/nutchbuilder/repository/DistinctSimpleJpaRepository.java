package com.jianglibo.nutchbuilder.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.jianglibo.nutchbuilder.util.QuerySpecUtil;

import io.katharsis.queryspec.QuerySpec;

public abstract class DistinctSimpleJpaRepository<T> extends SimpleJpaRepository<T, Long> implements RepositoryBase<T>{
	
	public DistinctSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}
	
	public List<T> findAll(QuerySpec querySpec) {
		Optional<Long> id = QuerySpecUtil.hasId(querySpec);
		if (id.isPresent()) {
			List<T> l = new ArrayList<>();
			T e = findOne(id.get());
			if (e != null) {
				l.add(e);
			}
			return l;
		} else {
			return findIfNotFindOne(querySpec);
		}
	}
	
	public long count(QuerySpec querySpec) {
		Optional<Long> id = QuerySpecUtil.hasId(querySpec);
		if (id.isPresent()) {
			T e = findOne(id.get());
			if (e != null) {
				return 1;
			}
			return 0;
		} else {
			return countIfNotCountOne(querySpec);
		}
	}
	
	protected long countBySpecifiation(Specification<T> spec) {
		if (spec == null) {
			return count();
		} else {
			return count(spec);
		}
	}
	
	protected List<T> findBySpecifiation(Specification<T> spec, Pageable pageable) {
		if (spec == null) {
			return findAll(pageable).getContent();
		} else {
			return findAll(spec, pageable).getContent();
		}
	}
	
	protected abstract long countIfNotCountOne(QuerySpec querySpec);

	protected abstract List<T> findIfNotFindOne(QuerySpec querySpec);

}
