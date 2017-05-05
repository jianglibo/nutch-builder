package com.jianglibo.nutchbuilder.facade;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jianglibo.nutchbuilder.constant.PreAuthorizeExpression;
import com.jianglibo.nutchbuilder.domain.MySite;

public interface MySiteFacadeRepository extends FacadeRepositoryBase<MySite> {

	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	MySite save(MySite entity);
	
}
