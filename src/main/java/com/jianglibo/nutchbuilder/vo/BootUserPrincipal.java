package com.jianglibo.nutchbuilder.vo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.BootUser.Gender;
import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

/**
 * @author jianglibo@gmail.com
 *
 */
public class BootUserPrincipal extends User {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final long id;

    private final String email;
    private final String mobile;
    private final String avatar;
    private final String displayName;

    private final Gender gender;

    private final String openId;

    private final boolean emailVerified;
    private final boolean mobileVerified;

    private final Set<ThirdPartLoginVo> thirdConns;

    public BootUserPrincipal() {
        super("-1", "", new HashSet<>());
        this.id = 0;
        this.email = null;
        this.mobile = null;
        this.avatar = null;
        this.displayName = null;
        this.emailVerified = false;
        this.mobileVerified = false;
        this.openId = "";
        this.gender = Gender.FEMALE;
        this.thirdConns = new HashSet<>();
    }

    public BootUserPrincipal(BootUser bu) {
        this(bu, bu.getRoles());
    }
    
    public BootUserPrincipal(UserDto dto) {
    	this(dto.getName()
    			,dto.getDisplayName()
    			,dto.getEmail()
    			,dto.getMobile()
    			,dto.getPassword()
    			,dto.isEnabled()
    			,dto.isAccountNonExpired()
    			,dto.isCredentialsNonExpired()
    			,dto.isAccountNonLocked()
    			,dto.getAvatar()
    			,dto.getRoles().stream().map(r -> new Role(r.getName())).collect(Collectors.toSet())
    			,dto.isEmailVerified()
    			,dto.isMobileVerified()
    			,dto.getGender()
    			,new HashSet<>()
    			,dto.getId() == null ? 0 : dto.getId()
    			,null);
    }

    public BootUserPrincipal(BootUser bootUser, Set<Role> roles) {
        this(bootUser.getName()
        		,bootUser.getDisplayName()
        		,bootUser.getEmail()
        		,bootUser.getMobile()
        		,bootUser.getPassword()
        		,bootUser.isEnabled()
        		,bootUser.isAccountNonExpired()
        		,bootUser.isCredentialsNonExpired()
        		,bootUser.isAccountNonLocked()
        		,bootUser.getAvatar()
        		,roles
        		,bootUser.isEmailVerified()
        		,bootUser.isMobileVerified()
        		,bootUser.getGender()
        		,ThirdPartLoginVo.toVos(bootUser.getThirdConns())
        		,bootUser.getId()
        		,bootUser.getOpenId());
    }

    public BootUserPrincipal(String name
    		,String displayName
    		,String email
    		,String mobile
    		,String password
    		,boolean enabled
    		,boolean accountNonExpired
    		,boolean credentialNonExpired
    		,boolean accountNonLocked
    		,String avatar
    		,Collection<? extends GrantedAuthority> authorities
    		,boolean emailVerified
    		,boolean mobileVerified
    		,Gender gender
    		,Set<ThirdPartLoginVo> thirdConns
    		,long id
    		,String openId) {
        super(name, password, enabled, accountNonExpired, credentialNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.mobile = mobile;
        this.avatar = avatar;
        this.emailVerified = emailVerified;
        this.mobileVerified = mobileVerified;
        if (openId == null || openId.isEmpty()) {
            this.openId = UUID.randomUUID().toString().replaceAll("-", "");
        } else {
            this.openId = openId;
        }
        this.gender = gender;
        this.thirdConns = thirdConns;
    }

    public BootUserPrincipal(String name
    		,String displayName
    		,String email
    		,String mobile
    		,String password
    		,boolean enabled
    		,boolean accountNonExpired
    		,boolean credentialNonExpired
            ,boolean accountNonLocked
            ,String avatar
            ,Collection<? extends GrantedAuthority> authorities
            ,boolean emailVerified
            ,boolean mobileVerified) {
        this(name, displayName, email, mobile, password, enabled, accountNonExpired, credentialNonExpired, accountNonLocked, avatar, authorities,
                emailVerified, mobileVerified, Gender.FEMALE, new HashSet<>(), 0, null);
    }

    public BootUserPrincipal(String name, String displayName, String email, String mobile, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialNonExpired, boolean accountNonLocked, String avatar, Collection<GrantedAuthority> authorities) {
        this(name, displayName, email, mobile, password, enabled, accountNonExpired, credentialNonExpired, accountNonLocked, avatar, authorities, false, false,
                Gender.FEMALE, new HashSet<>(), 0, null);
    }

    public BootUserPrincipal(String name, String displayName, String email, String mobile, String password, String avatar, Collection<GrantedAuthority> authorities) {
        this(name, displayName, email, mobile, password, true, true, true, true, avatar, authorities);
    }

    public BootUserPrincipal(String name, String email, String mobile, String password) {
        this(name, name, email, mobile, password, true, true, true, true, null, new HashSet<>());
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getId() {
        return id;
    }

    public String getOpenId() {
        return openId;
    }

    public Gender getGender() {
        return gender;
    }

    public Set<ThirdPartLoginVo> getThirdConns() {
        return thirdConns;
    }

}
