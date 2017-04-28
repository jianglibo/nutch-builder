package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.jianglibo.nutchbuilder.annotation.DtoToEntity;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.BootUser.Gender;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.BOOT_USER)
@DtoToEntity(entityClass=BootUser.class)
public class UserDto extends DtoBase<UserDto, BootUser> {
	
	public static interface OnCreateGroup {}
	
	private boolean updatePassword;
	
    private String displayName;

    private String avatar;

    private boolean emailVerified;

    private boolean mobileVerified;
    
    private Gender gender;
    
    @JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.LAZY, opposite="creator")
    private List<SiteDto> sites = new ArrayList<>();
    
    @NotNull
    @Size(min=6, max=36)
    private String name;

    @NotNull
    @Size(min=6, max=36)
    @Email
    private String email;

    @NotNull
    @Size(min=6, max=36, groups=OnCreateGroup.class)
    private String password;

    @NotNull
    @Size(min=8, max=16)
    private String mobile;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;
    
    private List<RoleDto> roles = new ArrayList<>();
    
    public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	@Override
    public UserDto fromEntity(BootUser bu) {
    	setAccountNonExpired(bu.isAccountNonExpired());
    	setAccountNonLocked(bu.isAccountNonLocked());
    	setAvatar(bu.getAvatar());
    	setCredentialsNonExpired(bu.isCredentialsNonExpired());
    	setDisplayName(bu.getDisplayName());
    	setEmail(bu.getEmail());
    	setEmailVerified(bu.isEmailVerified());
    	setEnabled(bu.isEnabled());
    	setGender(bu.getGender());
    	setId(bu.getId());
    	setMobile(bu.getMobile());
    	setMobileVerified(bu.isMobileVerified());
    	setName(bu.getName());
    	setPassword(null);
    	return this;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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

	@Override
	public BootUser patch(BootUser entity) {
		entity.setAccountNonExpired(isAccountNonExpired());
		entity.setAccountNonLocked(isAccountNonLocked());
		entity.setAvatar(getAvatar());
		entity.setCredentialsNonExpired(isCredentialsNonExpired());
		entity.setDisplayName(getDisplayName());
		entity.setEmail(getEmail());
		entity.setEmailVerified(isEmailVerified());
		entity.setEnabled(isEnabled());
		entity.setGender(getGender());
		entity.setId(getId());
		entity.setMobile(getMobile());
		entity.setMobileVerified(isMobileVerified());
		entity.setName(getName());
//		entity.setPassword(getPassword());
		return entity;
	}

	public boolean isUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(boolean updatePassword) {
		this.updatePassword = updatePassword;
	}

	public List<SiteDto> getSites() {
		return sites;
	}

	public void setSites(List<SiteDto> sites) {
		this.sites = sites;
	}
}
