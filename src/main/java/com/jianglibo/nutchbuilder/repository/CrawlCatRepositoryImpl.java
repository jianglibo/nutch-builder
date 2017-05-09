package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.katharsis.vo.SimplePageable;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class CrawlCatRepositoryImpl extends DistinctSimpleJpaRepository<CrawlCat> implements RepositoryBase<CrawlCat> {

    @Autowired
    public CrawlCatRepositoryImpl(EntityManager entityManager) {
        super(CrawlCat.class, entityManager);
    }

	@Override
	protected long countIfNotCountOne(QuerySpec querySpec) {
		return countBySpecifiation(null);
	}

	@Override
	protected List<CrawlCat> findIfNotFindOne(QuerySpec querySpec) {
		return findBySpecifiation(null, new SimplePageable(querySpec));
	}
    


}
