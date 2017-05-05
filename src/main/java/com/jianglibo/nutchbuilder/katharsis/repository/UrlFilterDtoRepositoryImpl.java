package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.UrlFilter;
import com.jianglibo.nutchbuilder.facade.MySiteFacadeRepository;
import com.jianglibo.nutchbuilder.facade.UrlFilterFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UrlFilterDto;
import com.jianglibo.nutchbuilder.katharsis.repository.UrlFilterDtoRepository.UrlFilterDtoList;


@Component
public class UrlFilterDtoRepositoryImpl  extends DtoRepositoryBase<UrlFilterDto, UrlFilterDtoList, UrlFilter, UrlFilterFacadeRepository> implements UrlFilterDtoRepository {
	
	private final MySiteFacadeRepository mySiteRepository;
	
	@Autowired
	public UrlFilterDtoRepositoryImpl(UrlFilterFacadeRepository repository, MySiteFacadeRepository mySiteRepository) {
		super(UrlFilterDto.class, UrlFilterDtoList.class, UrlFilter.class, repository);
		this.mySiteRepository = mySiteRepository;
	}
	
	@Override
	public UrlFilter saveToJpaRepo(UrlFilterDto dto, UrlFilter entity) {
		MySiteDto sd = dto.getMysite();
		entity.setMysite(mySiteRepository.findOne(sd.getId()));
		return getRepository().save(entity);
	}
}
