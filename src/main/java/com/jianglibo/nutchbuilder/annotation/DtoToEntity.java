package com.jianglibo.nutchbuilder.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jianglibo.nutchbuilder.domain.BaseEntity;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface DtoToEntity {
	Class<? extends BaseEntity> entityClass();
}
