package com.jianglibo.nutchbuilder.util;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.vo.BootUserAuthentication;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;


/**
 * 
 * @author jianglibo@gmail.com
 *
 */
public class SecurityUtil {

    public static void loginAs(Authentication au) {
        au.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(au);
    }


    public static Optional<BootUserPrincipal> getLoginBootUserVo() {
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        if (au == null || au instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        } else {
            return Optional.of((BootUserPrincipal) au.getPrincipal());
        }
    }
    
    public static void addUserToModel(Model model) {
    	if (getLoginBootUserVo().isPresent()) {
    		model.addAttribute("bootUser", getLoginBootUserVo().get());
    	}
    }
    
    public static long getLoginUserId() {
        if (getLoginBootUserVo().isPresent()) {
            return getLoginBootUserVo().get().getId();
        } else {
            return Long.MIN_VALUE;
        }
    }

    public static Authentication getLoginAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void doLogin(BootUser bootUser) {
        doLogin(SecurityContextHolder.getContext(), bootUser);
    }
    
    public static void doLogin(BootUserAuthentication mat) {
        SecurityContextHolder.getContext().setAuthentication(mat);
    }


    public static boolean hasRole(String rn) {
        if (getLoginAuthentication() == null) {
            return false;
        }
        if (getLoginAuthentication().isAuthenticated()) {
            return getLoginAuthentication().getAuthorities().stream().anyMatch(ga -> rn.equals(ga.getAuthority()));
        }
        return false;
    }
    
    public static void logout() {
        SecurityContextHolder.clearContext();
    }
    
    
    public static boolean hasAnyRole(String...rns) {
        return Stream.of(rns).anyMatch(SecurityUtil::hasRole);
    }

    public static SecurityContext doLogin(SecurityContext context, BootUser bootUser) {
        BootUserAuthentication uat = new BootUserAuthentication(new BootUserPrincipal(bootUser));
        context.setAuthentication(uat);
        return context;
    }
}
