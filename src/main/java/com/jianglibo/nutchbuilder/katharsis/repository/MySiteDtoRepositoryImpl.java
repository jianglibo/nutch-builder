package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.MySite;
import com.jianglibo.nutchbuilder.facade.MySiteFacadeRepository;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.repository.MySiteDtoRepository.MySiteDtoList;

@Component
public class MySiteDtoRepositoryImpl  extends DtoRepositoryBase<MySiteDto, MySiteDtoList, MySite, MySiteFacadeRepository> implements MySiteDtoRepository {
	
	private final SiteFacadeRepository siteRepository;
	
	@Autowired
	public MySiteDtoRepositoryImpl(MySiteFacadeRepository repository, SiteFacadeRepository siteRepository) {
		super(MySiteDto.class, MySiteDtoList.class, MySite.class, repository);
		this.siteRepository = siteRepository;
	}

	@Override
	public MySite saveToJpaRepo(MySiteDto dto, MySite entity) {
		
		return getRepository().save(entity);
	}
	
}
