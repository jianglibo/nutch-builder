package com.jianglibo.nutchbuilder.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.jianglibo.nutchbuilder.domain.Role;

/**
 * @author jianglibo@gmail.com
 *
 */
public class RoleRepositoryImpl extends DistinctSimpleJpaRepository<Role> implements RoleRepositoryCustom {

//	private final JpaEntityInformation<Role, ?> entityInformation;
    
    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        super(Role.class, entityManager);
//        this.entityInformation = JpaEntityInformationSupport.getMetadata(Role.class, entityManager);
    }
    
    @Override
	public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
		return super.findAll(example, pageable);
	}

}
