package com.jianglibo.nutchbuilder.config;

import org.junit.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.jianglibo.nutchbuilder.Tbase;

public class TestProtectedMethod extends Tbase {
	
	@Test(expected=AuthenticationCredentialsNotFoundException.class)
	public void t() {
		ProtectedMethod pm = (ProtectedMethod) context.getBeansOfType(Pmi.class).values().iterator().next();
		pm.m();
	}

}
