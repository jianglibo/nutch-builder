package com.jianglibo.nutchbuilder.facade;

import com.jianglibo.nutchbuilder.domain.LoginAttempt;


public interface LoginAttemptFacadeRepository extends FacadeRepositoryBase<LoginAttempt>{
    
    @Override
    LoginAttempt save(LoginAttempt entity);
}
