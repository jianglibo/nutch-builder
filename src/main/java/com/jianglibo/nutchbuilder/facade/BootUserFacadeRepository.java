package com.jianglibo.nutchbuilder.facade;

import com.jianglibo.nutchbuilder.domain.BootUser;

public interface BootUserFacadeRepository extends FacadeRepositoryBase<BootUser> {

	BootUser findByEmail(String emailOrMobile);

	BootUser findByMobile(String emailOrMobile);

	BootUser findByName(String emailOrMobile);

}
