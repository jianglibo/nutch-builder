package com.jianglibo.nutchbuilder.facade.jpa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.facade.BootUserFacadeRepository;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class BootUserFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<BootUser, BootUserRepository> implements BootUserFacadeRepository {

	@Autowired
	public BootUserFacadeRepositoryImpl(BootUserRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public BootUser findByEmail(String email) {
		return getRepository().findByEmail(email);
	}

	@Override
	public BootUser findByMobile(String mobile) {
		return getRepository().findByMobile(mobile);
	}

	@Override
	public BootUser findByName(String name) {
		return getRepository().findByName(name);
	}
}
