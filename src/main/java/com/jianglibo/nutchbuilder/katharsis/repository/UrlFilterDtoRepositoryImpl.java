package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.UrlFilter;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.facade.UrlFilterFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UrlFilterDto;
import com.jianglibo.nutchbuilder.katharsis.repository.UrlFilterDtoRepository.UrlFilterDtoList;


@Component
public class UrlFilterDtoRepositoryImpl  extends DtoRepositoryBase<UrlFilterDto, UrlFilterDtoList, UrlFilter, UrlFilterFacadeRepository> implements UrlFilterDtoRepository {
	
	private final SiteFacadeRepository siteRepository;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public UrlFilterDtoRepositoryImpl(UrlFilterFacadeRepository repository, SiteFacadeRepository siteRepository) {
		super(UrlFilterDto.class, UrlFilterDtoList.class, UrlFilter.class, repository);
		this.siteRepository = siteRepository;
	}
	
	@Override
	public UrlFilter saveToJpaRepo(UrlFilterDto dto, UrlFilter entity) {
		SiteDto sd = dto.getSite();
		entity.setSite(siteRepository.findOne(sd.getId()));
		return getRepository().save(entity);
	}
}
