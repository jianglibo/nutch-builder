package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.jianglibo.nutchbuilder.domain.SourceFile;

/**
 * @author jianglibo@gmail.com
 *
 */
public class SourceFileRepositoryImpl extends SimpleJpaRepository<SourceFile, Long> implements SourceFileRepositoryCustom {

    
    @Autowired
    public SourceFileRepositoryImpl(EntityManager entityManager) {
        super(SourceFile.class, entityManager);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#save(java.lang.Object)
     */
    @Override
    @Transactional
    public <S extends SourceFile> S save(S entity) {
        return super.save(entity);
    }

}
