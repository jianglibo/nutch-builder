package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.LoginAttempt;
import com.jianglibo.nutchbuilder.facade.LoginAttemptFacadeRepository;
import com.jianglibo.nutchbuilder.repository.LoginAttemptRepository;

@Component
public class LoginAttemptFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<LoginAttempt, LoginAttemptRepository> implements  LoginAttemptFacadeRepository {

	public LoginAttemptFacadeRepositoryImpl(LoginAttemptRepository jpaRepo) {
		super(jpaRepo);
	}
}
