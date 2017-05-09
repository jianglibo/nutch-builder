package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class NutchBuilderTemplateRepositoryImpl extends SimpleJpaRepositoryBase<NutchBuilderTemplate> implements RepositoryBase<NutchBuilderTemplate> {

    @Autowired
    public NutchBuilderTemplateRepositoryImpl(EntityManager entityManager) {
        super(NutchBuilderTemplate.class, entityManager);
    }
    
//	@Override
//	protected long countIfNotCountOne(QuerySpec querySpec) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	protected List<NutchBuilderTemplate> findIfNotFindOne(QuerySpec querySpec) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
