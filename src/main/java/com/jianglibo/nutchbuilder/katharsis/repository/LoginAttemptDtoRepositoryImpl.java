package com.jianglibo.nutchbuilder.katharsis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.LoginAttempt;
import com.jianglibo.nutchbuilder.katharsis.dto.LoginAttemptDto;
import com.jianglibo.nutchbuilder.katharsis.repository.LoginAttemptDtoRepository.LoginAttemptDtoList;
import com.jianglibo.nutchbuilder.repository.LoginAttemptRepository;

@Component
public class LoginAttemptDtoRepositoryImpl  extends DtoRepositoryBase<LoginAttemptDto, LoginAttemptDtoList, LoginAttempt> implements LoginAttemptDtoRepository {
	
	private final LoginAttemptRepository repository;

	@Autowired
	public LoginAttemptDtoRepositoryImpl(LoginAttemptRepository repository) {
		super(LoginAttemptDto.class, LoginAttemptDtoList.class, LoginAttempt.class, repository);
		this.repository = repository;
	}

}
