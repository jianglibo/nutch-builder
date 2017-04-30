package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.Site;

public interface SiteFacadeRepository extends FacadeRepositoryBase<Site> {

	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	Site  save(Site entity);
	
}
