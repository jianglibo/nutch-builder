package com.jianglibo.nutchbuilder.katharsis.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jianglibo.nutchbuilder.domain.BootUser;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = "users")
public class UserDto {
	
	@JsonApiId
	private Long id;
	
    private String displayName;

    private String avatar;

    private boolean emailVerified;

    private boolean mobileVerified;
    
    private String gender;
    
    @NotNull
    private String name;

    @NotNull
    private String email;

    @JsonIgnore
    private String password;

    private String mobile;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;
    
    
    public UserDto fromEntity(BootUser bu) {
    	setAccountNonExpired(bu.isAccountNonExpired());
    	setAccountNonLocked(bu.isAccountNonLocked());
    	setAvatar(bu.getAvatar());
    	setCredentialsNonExpired(bu.isCredentialsNonExpired());
    	setDisplayName(bu.getDisplayName());
    	setEmail(bu.getEmail());
    	setEmailVerified(bu.isEmailVerified());
    	setEnabled(bu.isEnabled());
    	setGender(bu.getGender().name());
    	setId(bu.getId());
    	setMobile(bu.getMobile());
    	setMobileVerified(bu.isMobileVerified());
    	setName(bu.getName());
    	setPassword(bu.getPassword());
    	return this;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
    
}
