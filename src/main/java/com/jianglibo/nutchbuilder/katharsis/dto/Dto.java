package com.jianglibo.nutchbuilder.katharsis.dto;

public interface Dto<T, E> {

	T fromEntity(E entity);
	
	Long getId();
	
	E patch(E entity);
	
	default Long a() {
		return 0L;
	}
	
}
