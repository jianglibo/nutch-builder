package com.jianglibo.nutchbuilder.katharsis.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.LoginAttempt;
import com.jianglibo.nutchbuilder.facade.BootUserFacadeRepository;
import com.jianglibo.nutchbuilder.facade.LoginAttemptFacadeRepository;
import com.jianglibo.nutchbuilder.jwt.JwtUtil;
import com.jianglibo.nutchbuilder.katharsis.dto.LoginAttemptDto;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.repository.LoginAttemptDtoRepository.LoginAttemptDtoList;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil.RelationQuery;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

import io.katharsis.queryspec.QuerySpec;

@Component
@Transactional
public class LoginAttemptDtoRepositoryImpl  extends DtoRepositoryBase<LoginAttemptDto, LoginAttemptDtoList, LoginAttempt, LoginAttemptFacadeRepository> implements LoginAttemptDtoRepository {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private BootUserFacadeRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private AuthenticationManager getAuthenticationManager() {
		return applicationContext.getBean(AuthenticationManager.class);
	}

	@Autowired
	public LoginAttemptDtoRepositoryImpl(LoginAttemptFacadeRepository repository) {
		super(LoginAttemptDto.class, LoginAttemptDtoList.class, LoginAttempt.class, repository);
	}
	
	@Override
	public LoginAttemptDto createNew(LoginAttemptDto dto) {
		return invoke(dto);
	}
	
	@Override
	public LoginAttemptDto modify(LoginAttemptDto dto) {
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
				getRepository().save(loginAttemp);
				dto.setId(loginAttemp.getId());
				dto.setSuccess(true);
				dto.setPassword("");
				dto.setJwtToken(jwtUtil.issuePrincipalToken(user));
				BootUser bu = userRepository.findOne(user.getId());
				UserDto udto = new UserDto().fromEntity(bu);
				udto.setRoles(bu.getRoles().stream().map(r -> new RoleDto().fromEntity(r)).collect(Collectors.toList()));
				dto.setUser(udto);
				return dto;
		} catch (AuthenticationException e) {
				getRepository().save(loginAttemp);
				throw e;
		}
	}

	@Override
	protected LoginAttemptDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected LoginAttemptDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
