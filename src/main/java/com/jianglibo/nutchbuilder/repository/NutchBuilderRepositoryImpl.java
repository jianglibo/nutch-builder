package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.NutchBuilder;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class NutchBuilderRepositoryImpl extends SimpleJpaRepositoryBase<NutchBuilder> implements RepositoryBase<NutchBuilder>{

    
    @Autowired
    public NutchBuilderRepositoryImpl(EntityManager entityManager) {
        super(NutchBuilder.class, entityManager);
    }
    
//	@Override
//	protected long countIfNotCountOne(QuerySpec querySpec) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	protected List<NutchBuilder> findIfNotFindOne(QuerySpec querySpec) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
