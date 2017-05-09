package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.facade.SimplePageable;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class SiteRepositoryImpl extends SimpleJpaRepositoryBase<Site> implements RepositoryBase<Site> {

    @Autowired
    public SiteRepositoryImpl(EntityManager entityManager) {
        super(Site.class, entityManager);
    }
    
//    @Override
//    public List<Site> findAll(QuerySpec querySpec) {
//    	return super.findAll(querySpec);
//    }
//
//	@Override
//	protected long countIfNotCountOne(QuerySpec querySpec) {
//		return countBySpecifiation(null);
//	}
//
//	@Override
//	protected List<Site> findIfNotFindOne(QuerySpec querySpec) {
//		return findBySpecifiation(null, new SimplePageable(querySpec));
//	}

}
