package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.domain.Site;

public interface SiteFacadeRepository extends FacadeRepositoryBase<Site> {

	@PreAuthorize("isFullyAuthenticated()")
	Site  save(@P("entity")Site entity);
	
}
