package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.Site;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class SiteRepositoryImpl extends DistinctSimpleJpaRepository<Site> implements RepositoryBase<Site> {

    @Autowired
    public SiteRepositoryImpl(EntityManager entityManager) {
        super(Site.class, entityManager);
    }
    
    /**
     * maybe disabling arbitrary filter feather is judicious.
     */
	@Override
	protected Specification<Site> createSpecification(QuerySpec querySpec) {
//		return RoleSpecifications.nameLike(filterValue(querySpec, "name"));
		return null;
	}

}
