package com.jianglibo.nutchbuilder.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import com.jianglibo.nutchbuilder.domain.LoginAttempt;

import io.katharsis.queryspec.QuerySpec;

public class LoginAttemptRepositoryImpl extends DistinctSimpleJpaRepository<LoginAttempt>  implements  ApplicationContextAware, RepositoryBase<LoginAttempt> {

    @SuppressWarnings("unused")
    private EntityManager entityManager;

    @SuppressWarnings("unused")
    private ApplicationContext context;

    @SuppressWarnings("unused")
    private final JpaEntityInformation<LoginAttempt, ?> entityInformation;
    
    
    @Autowired
    public LoginAttemptRepositoryImpl(EntityManager entityManager) {
        super(LoginAttempt.class, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(LoginAttempt.class, entityManager);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

	@Override
	protected long countIfNotCountOne(QuerySpec querySpec) {
		return 0;
	}

	@Override
	protected List<LoginAttempt> findIfNotFindOne(QuerySpec querySpec) {
		return null;
	}
}
