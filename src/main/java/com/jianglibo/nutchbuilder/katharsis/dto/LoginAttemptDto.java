package com.jianglibo.nutchbuilder.katharsis.dto;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.LoginAttempt;
import com.jianglibo.nutchbuilder.domain.ThirdPartLogin.Provider;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.LOGIN_ATTEMPT)
@DtoToEntity(entityClass=LoginAttempt.class)
public class LoginAttemptDto implements Dto<LoginAttemptDto, LoginAttempt>{
	
	@JsonApiId
	private Long id;
	
    private String username;
    
    private String password;

	private String remoteAddress;
	
	private String sessionId;
	
	private Provider provider = Provider.NORMAL;
	
	private boolean success;

	@Override
	public LoginAttemptDto fromEntity(LoginAttempt entity) {
		setId(entity.getId());
		setPassword(entity.getPassword());
		setProvider(entity.getProvider());
		setRemoteAddress(entity.getRemoteAddress());
		setSessionId(entity.getSessionId());
		setSuccess(entity.isSuccess());
		setUsername(entity.getUsername());
		return this;
	}

	@Override
	public LoginAttempt patch(LoginAttempt entity) {
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
