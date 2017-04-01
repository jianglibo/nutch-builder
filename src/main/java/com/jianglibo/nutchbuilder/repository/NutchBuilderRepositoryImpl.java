package com.jianglibo.nutchbuilder.repository;

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
public class NutchBuilderRepositoryImpl extends DistinctSimpleJpaRepository<NutchBuilder> implements RepositoryBase<NutchBuilder>{

    
    @Autowired
    public NutchBuilderRepositoryImpl(EntityManager entityManager) {
        super(NutchBuilder.class, entityManager);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#save(java.lang.Object)
     */
    @Override
    @Transactional
    public <S extends NutchBuilder> S save(S entity) {
        return super.save(entity);
    }

	@Override
	protected Specification<NutchBuilder> createSpecification(QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}

}
