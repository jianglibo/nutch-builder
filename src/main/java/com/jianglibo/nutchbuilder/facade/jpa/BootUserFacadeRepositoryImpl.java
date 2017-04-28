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
public class BootUserFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<BootUser> implements BootUserFacadeRepository {

	@Autowired
	public BootUserFacadeRepositoryImpl(BootUserRepository jpaRepo) {
		super(jpaRepo);
	}
}
