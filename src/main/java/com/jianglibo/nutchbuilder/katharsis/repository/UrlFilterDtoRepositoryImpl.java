package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.UrlFilter;
import com.jianglibo.nutchbuilder.katharsis.dto.UrlFilterDto;
import com.jianglibo.nutchbuilder.katharsis.repository.UrlFilterDtoRepository.UrlFilterDtoList;
import com.jianglibo.nutchbuilder.repository.UrlFilterRepository;


@Component
public class UrlFilterDtoRepositoryImpl  extends DtoRepositoryBase<UrlFilterDto, UrlFilterDtoList, UrlFilter> implements UrlFilterDtoRepository {
	
	private final UrlFilterRepository repository;
	
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public UrlFilterDtoRepositoryImpl(UrlFilterRepository repository) {
		super(UrlFilterDto.class, UrlFilterDtoList.class, UrlFilter.class, repository);
		this.repository = repository;
	}
}
