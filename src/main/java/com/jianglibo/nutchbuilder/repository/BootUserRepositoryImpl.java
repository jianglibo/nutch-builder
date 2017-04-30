package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.BootUser;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class BootUserRepositoryImpl extends DistinctSimpleJpaRepository<BootUser> implements RepositoryBase<BootUser>{

    @Autowired
    public BootUserRepositoryImpl(EntityManager entityManager) {
        super(BootUser.class, entityManager);
    }

	@Override
	protected Specification<BootUser> createSpecification(QuerySpec querySpec) {
		return null;
	}
}
