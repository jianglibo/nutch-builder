package com.jianglibo.nutchbuilder.repository;

//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;

import com.jianglibo.nutchbuilder.domain.LoginAttempt;


//@RepositoryRestResource(collectionResourceRel = "loginattempt", path = "loginattempts")
public interface LoginAttemptRepository extends RepositoryBase<LoginAttempt>{
    
    @Override
//    @RestResource(exported = false)
    <S extends LoginAttempt> S save(S entity);
}
