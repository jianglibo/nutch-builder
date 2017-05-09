package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.domain.CrawlFrequency;
import com.jianglibo.nutchbuilder.facade.SimplePageable;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class CrawlFrequencyRepositoryImpl extends SimpleJpaRepositoryBase<CrawlFrequency> implements RepositoryBase<CrawlFrequency> {

    @Autowired
    public CrawlFrequencyRepositoryImpl(EntityManager entityManager) {
        super(CrawlFrequency.class, entityManager);
    }

//	@Override
//	protected long countIfNotCountOne(QuerySpec querySpec) {
//		return countBySpecifiation(null);
//	}
//
//	@Override
//	protected List<CrawlFrequency> findIfNotFindOne(QuerySpec querySpec) {
//		return findBySpecifiation(null, new SimplePageable(querySpec));
//	}

}
