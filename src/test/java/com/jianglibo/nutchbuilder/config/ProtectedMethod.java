package com.jianglibo.nutchbuilder.config;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import io.katharsis.repository.Repository;

@Component
public class ProtectedMethod implements Pmi {

	@PreAuthorize("hasRole('AROLENAME')")
	public void m() {
		ProtectedMethod.class.isAssignableFrom(Repository.class);
	}
}
