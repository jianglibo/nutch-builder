package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.MySite;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class MySiteRepositoryImpl extends DistinctSimpleJpaRepository<MySite> implements RepositoryBase<MySite> {

    @Autowired
    public MySiteRepositoryImpl(EntityManager entityManager) {
        super(MySite.class, entityManager);
    }
    
    /**
     * maybe disabling arbitrary filter feather is judicious.
     */
	@Override
	protected Specification<MySite> createSpecification(QuerySpec querySpec) {
//		return RoleSpecifications.nameLike(filterValue(querySpec, "name"));
		return null;
	}

}
