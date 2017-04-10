package com.jianglibo.nutchbuilder.katharsis.dto;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.LoginAttempt;
import com.jianglibo.nutchbuilder.domain.ThirdPartLogin.Provider;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.meta.MetaInformation;

@JsonApiResource(type = JsonApiResourceNames.LOGIN_ATTEMPT)
@DtoToEntity(entityClass=LoginAttempt.class)
public class LoginAttemptDto extends  DtoBase<LoginAttemptDto, LoginAttempt>{
	
    private String username;
    
    private String password;

	private String remoteAddress;
	
	private String sessionId;
	
	private String jwtToken;
	
	private UserDto user;
	
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

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	private UserLinks links;
	
    public static class UserLinks implements MetaInformation {

        private String value;

        public String getValue() {
                return value;
        }

        public void setValue(String value) {
                this.value = value;
        }
    }
	
}
