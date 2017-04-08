package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.nutchbuilder.domain.CrawlFrequency;

import io.katharsis.queryspec.QuerySpec;

/**
 * @author jianglibo@gmail.com
 *
 */
public class CrawlFrequencyRepositoryImpl extends DistinctSimpleJpaRepository<CrawlFrequency> implements RepositoryBase<CrawlFrequency> {

    @Autowired
    public CrawlFrequencyRepositoryImpl(EntityManager entityManager) {
        super(CrawlFrequency.class, entityManager);
    }
    
    /**
     * maybe disabling arbitrary filter feather is judicious.
     */
	@Override
	protected Specification<CrawlFrequency> createSpecification(QuerySpec querySpec) {
		return null;
	}

}
