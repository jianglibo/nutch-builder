package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

public interface Dto<T, E> {

	T fromEntity(E entity);
	
	E patch(E entity);
	
	default List<T> batchConvert(Iterable<E> entities) {
		List<T> dtos = new ArrayList<>();
		for(E entity : entities) {
			dtos.add(fromEntity(entity));
		}
		return dtos;
	}
	
	default Long a() {
		return 0L;
	}
	
}
