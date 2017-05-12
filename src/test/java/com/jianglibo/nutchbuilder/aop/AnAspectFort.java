package com.jianglibo.nutchbuilder.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AnAspectFort {
	
	@Pointcut("execution(public * io.katharsis.repository.RelationshipRepositoryBase.findManyTargets(*,*,*))")
	public void findManyTargets(){}
	
	
	@Around("findManyTargets()")
	public Object roundFindManyTargets(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = pjp.proceed(pjp.getArgs());
		return retVal;
	}
}
