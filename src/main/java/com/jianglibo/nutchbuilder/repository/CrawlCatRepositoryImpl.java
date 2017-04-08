package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.CrawlCat;

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
    
    /**
     * maybe disabling arbitrary filter feather is judicious.
     */
	@Override
	protected Specification<CrawlCat> createSpecification(QuerySpec querySpec) {
		return null;
	}

}
