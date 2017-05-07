package com.jianglibo.nutchbuilder.facade;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

public interface BootUserFacadeRepository extends FacadeRepositoryBase<BootUser> {

	BootUser findByEmail(String emailOrMobile);

	BootUser findByMobile(String emailOrMobile);

	BootUser findByName(String emailOrMobile);
	
	BootUser updatePassword(Long id, String encodedPassword);
	
	BootUser patch(BootUser user, UserDto userDto);

}
