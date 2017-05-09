package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.domain.MySite;
import com.jianglibo.nutchbuilder.facade.SimplePageable;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class MySiteRepositoryImpl extends SimpleJpaRepositoryBase<MySite> implements RepositoryBase<MySite> {

    @Autowired
    public MySiteRepositoryImpl(EntityManager entityManager) {
        super(MySite.class, entityManager);
    }
//    
//	@Override
//	protected long countIfNotCountOne(QuerySpec querySpec) {
//		return countBySpecifiation(null);
//	}
//
//	@Override
//	protected List<MySite> findIfNotFindOne(QuerySpec querySpec) {
//		return findBySpecifiation(null, new SimplePageable(querySpec));
//	}

}
