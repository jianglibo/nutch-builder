/**
 * 2016 jianglibo@gmail.com
 *
 */
package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;

/**
 * @author jianglibo@gmail.com
 *         2015年9月28日
 *
 */
public class NutchBuilderTemplateRepositoryImpl extends SimpleJpaRepository<NutchBuilderTemplate, Long> implements NutchBuilderTemplateRepositoryCustom {

    @Autowired
    public NutchBuilderTemplateRepositoryImpl(EntityManager entityManager) {
        super(NutchBuilderTemplate.class, entityManager);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#save(java.lang.Object)
     */
    @Override
    public <S extends NutchBuilderTemplate> S save(S entity) {
        return super.save(entity);
    }

}
