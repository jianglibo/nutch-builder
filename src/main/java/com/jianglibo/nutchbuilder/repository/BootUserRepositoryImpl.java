package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.jianglibo.nutchbuilder.domain.BootUser;

/**
 * @author jianglibo@gmail.com
 *
 */
public class BootUserRepositoryImpl extends SimpleJpaRepository<BootUser, Long> implements BootUserRepositoryCustom {

//	private final JpaEntityInformation<BootUser, ?> entityInformation;
    
    @Autowired
    public BootUserRepositoryImpl(EntityManager entityManager) {
        super(BootUser.class, entityManager);
//        this.entityInformation = JpaEntityInformationSupport.getMetadata(BootUser.class, entityManager);
    }
}
