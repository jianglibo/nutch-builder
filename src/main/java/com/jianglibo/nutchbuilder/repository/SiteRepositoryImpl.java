package com.jianglibo.nutchbuilder.repository;


import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.domain.Site;

/**
 * @author jianglibo@gmail.com
 *
 */
public class SiteRepositoryImpl extends SimpleJpaRepositoryBase<Site> implements RepositoryBase<Site> {

    @Autowired
    public SiteRepositoryImpl(EntityManager entityManager) {
        super(Site.class, entityManager);
    }
    
    
}
