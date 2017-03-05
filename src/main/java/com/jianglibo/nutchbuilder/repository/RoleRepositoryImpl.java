package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.jianglibo.nutchbuilder.domain.Role;

/**
 * @author jianglibo@gmail.com
 *         2015年9月28日
 *
 */
public class RoleRepositoryImpl extends SimpleJpaRepository<Role, Long> implements RoleRepositoryCustom {

//	private final JpaEntityInformation<Role, ?> entityInformation;
    
    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        super(Role.class, entityManager);
//        this.entityInformation = JpaEntityInformationSupport.getMetadata(Role.class, entityManager);
    }

}
