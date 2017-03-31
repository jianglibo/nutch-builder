package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.repository.specification.RoleSpecifications;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class RoleRepositoryImpl extends DistinctSimpleJpaRepository<Role> implements RepositoryBase<Role> {

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        super(Role.class, entityManager);
    }
    
    /**
     * maybe disabling arbitrary filter feather is judicious.
     */
	@Override
	protected Specification<Role> createSpecification(QuerySpec querySpec) {
		return RoleSpecifications.nameLike(filterValue(querySpec, "name"));
	}

}
