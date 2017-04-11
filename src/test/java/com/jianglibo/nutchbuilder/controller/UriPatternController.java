package com.jianglibo.nutchbuilder.controller;


import org.hibernate.jpa.internal.EntityManagerFactoryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;
import com.jianglibo.nutchbuilder.util.SecurityUtil;

@Controller
public class UriPatternController {
    
    @RequestMapping(value = {"/llapp/kkk/**/{noext:[^.]*}" }, method = RequestMethod.GET)
    public ResponseEntity<String> llapp(@PathVariable String noext) {
        return ResponseEntity.ok(noext);
    }
    
    @Autowired
    private BootUserRepository userRepository;
    
    @RequestMapping(value="/ppp", method = RequestMethod.GET)
    public ResponseEntity<String> invokeProtected() {
    	BootUser bu = userRepository.findByName("admin");
    	SecurityUtil.doLogin(bu);
    	try {
			userRepository.delete(bu);
		} catch (AccessDeniedException e) {
			return ResponseEntity.ok("denied");
		}
    	return null;
    }
    
}
