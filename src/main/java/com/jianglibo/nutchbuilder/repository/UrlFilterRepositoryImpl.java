package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.UrlFilter;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class UrlFilterRepositoryImpl extends DistinctSimpleJpaRepository<UrlFilter> implements RepositoryBase<UrlFilter> {

    @Autowired
    public UrlFilterRepositoryImpl(EntityManager entityManager) {
        super(UrlFilter.class, entityManager);
    }
    
    /**
     * maybe disabling arbitrary filter feather is judicious.
     */
	@Override
	protected Specification<UrlFilter> createSpecification(QuerySpec querySpec) {
//		return RoleSpecifications.nameLike(filterValue(querySpec, "name"));
		return null;
	}

}
