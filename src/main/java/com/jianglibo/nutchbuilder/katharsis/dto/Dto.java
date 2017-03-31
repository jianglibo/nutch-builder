package com.jianglibo.nutchbuilder.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

public interface Dto<T, E> {

	T fromEntity(E entity);
	
	E patch(E entity);
	
	static <D extends Dto<D, E>, E> List<D> convertToDto(Class<D> dtoClass, Iterable<E> entities) {
		List<D> dtos = new ArrayList<>();
		for(E entity : entities) {
			D dto;
			try {
				dto = dtoClass.newInstance();
				dtos.add(dto.fromEntity(entity));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	default Long a() {
		return 0L;
	}
	
}
