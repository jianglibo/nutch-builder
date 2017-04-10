package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.HttpRequestHolder;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.LoginAttempt;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.jwt.JwtUtil;
import com.jianglibo.nutchbuilder.katharsis.dto.LoginAttemptDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.repository.LoginAttemptDtoRepository.LoginAttemptDtoList;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;
import com.jianglibo.nutchbuilder.repository.LoginAttemptRepository;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

@Component
@Transactional
public class LoginAttemptDtoRepositoryImpl  extends DtoRepositoryBase<LoginAttemptDto, LoginAttemptDtoList, LoginAttempt> implements LoginAttemptDtoRepository {
	
	private final LoginAttemptRepository repository;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private BootUserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private AuthenticationManager getAuthenticationManager() {
		return applicationContext.getBean(AuthenticationManager.class);
	}

	@Autowired
	public LoginAttemptDtoRepositoryImpl(LoginAttemptRepository repository) {
		super(LoginAttemptDto.class, LoginAttemptDtoList.class, LoginAttempt.class, repository);
		this.repository = repository;
	}
	
	@Override
	public <S extends LoginAttemptDto> S createNew(S dto) {
		HttpRequestHolder request = applicationContext.getBean(HttpRequestHolder.class);
		return (S) invoke(dto);
	}
	
	@Override
	public <S extends LoginAttemptDto> S modify(S dto) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}
	
	@Transactional
	private LoginAttemptDto invoke(LoginAttemptDto dto) {
		dto.setJwtToken("");
		LoginAttempt loginAttemp = new LoginAttempt();
		loginAttemp.setProvider(dto.getProvider());
		loginAttemp.setUsername(dto.getUsername());
		
		try {
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(dto.getUsername(),
						dto.getPassword());
				Authentication an = getAuthenticationManager().authenticate(authRequest);
				BootUserPrincipal user = (BootUserPrincipal) an.getPrincipal();
				loginAttemp.setSuccess(true);
				loginAttemp.setPassword("");
				repository.save(loginAttemp);
				dto.setId(loginAttemp.getId());
				dto.setSuccess(true);
				dto.setPassword("");
				dto.setJwtToken(jwtUtil.issuePrincipalToken(user));
				BootUser bu = userRepository.getOne(user.getId());
				List<Site> sites = bu.getSites();
				dto.setUser(new UserDto().fromEntity(bu));
				return dto;
		} catch (AuthenticationException e) {
				repository.save(loginAttemp);
				throw e;
		}
	}
	
}
