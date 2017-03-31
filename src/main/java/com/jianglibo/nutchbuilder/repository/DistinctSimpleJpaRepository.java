package com.jianglibo.nutchbuilder.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.jianglibo.nutchbuilder.katharsis.dto.SimplePageable;

import io.katharsis.queryspec.FilterSpec;
import io.katharsis.queryspec.QuerySpec;

public abstract class DistinctSimpleJpaRepository<T> extends SimpleJpaRepository<T, Long>{
	
	public DistinctSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}
	
	
	public List<T> findAll(QuerySpec querySpec) {
		return findAll(createSpecification(querySpec), new SimplePageable(querySpec)).getContent();
	}
	
	public long count(QuerySpec querySpec) {
		return count(createSpecification(querySpec));
	}
	
	protected abstract Specification<T> createSpecification(QuerySpec querySpec);
	
	protected String filterValue(QuerySpec querySpec, String fn) {
		Optional<FilterSpec> ofs = querySpec.getFilters().stream().filter(f -> fn.equals(f.getAttributePath().get(0))).findAny();
		if (ofs.isPresent()) {
			Object v = ofs.get().getValue();
			if (v == null) {
				return null;
			}
			if (v instanceof String) {
				if (((String) v).trim().isEmpty()) {
					return null;
				} else {
					return ((String) v).trim();
				}
			} else {
				throw new RuntimeException(String.format("filter type is not implementation.", v.getClass().getName()));
			}
		} else {
			return null;
		}
	}

}
