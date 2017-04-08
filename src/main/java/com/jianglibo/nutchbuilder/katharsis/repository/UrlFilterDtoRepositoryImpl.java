package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.UrlFilter;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UrlFilterDto;
import com.jianglibo.nutchbuilder.katharsis.repository.UrlFilterDtoRepository.UrlFilterDtoList;
import com.jianglibo.nutchbuilder.repository.SiteRepository;
import com.jianglibo.nutchbuilder.repository.UrlFilterRepository;


@Component
public class UrlFilterDtoRepositoryImpl  extends DtoRepositoryBase<UrlFilterDto, UrlFilterDtoList, UrlFilter> implements UrlFilterDtoRepository {
	
	private final UrlFilterRepository repository;
	
	private final SiteRepository siteRepository;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public UrlFilterDtoRepositoryImpl(UrlFilterRepository repository, SiteRepository siteRepository) {
		super(UrlFilterDto.class, UrlFilterDtoList.class, UrlFilter.class, repository);
		this.repository = repository;
		this.siteRepository = siteRepository;
	}
	
	@Override
	public UrlFilter saveToJpaRepo(UrlFilterDto dto, UrlFilter entity) {
		SiteDto sd = dto.getSite();
		entity.setSite(siteRepository.findOne(sd.getId()));
		return repository.save(entity);
	}
}
