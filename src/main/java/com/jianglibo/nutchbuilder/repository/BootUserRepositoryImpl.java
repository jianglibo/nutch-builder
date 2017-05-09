package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.domain.BootUser;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class BootUserRepositoryImpl extends SimpleJpaRepositoryBase<BootUser> implements RepositoryBase<BootUser>{

    @Autowired
    public BootUserRepositoryImpl(EntityManager entityManager) {
        super(BootUser.class, entityManager);
    }

//	@Override
//	protected long countIfNotCountOne(QuerySpec querySpec) {
//		return 0;
//	}
//
//	@Override
//	protected List<BootUser> findIfNotFindOne(QuerySpec querySpec) {
//		return null;
//	}
}
