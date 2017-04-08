package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.repository.SiteRepository;
import com.jianglibo.nutchbuilder.katharsis.repository.SiteDtoRepository.SiteDtoList;

@Component
public class SiteDtoRepositoryImpl  extends DtoRepositoryBase<SiteDto, SiteDtoList, Site> implements SiteDtoRepository {
	
	private final SiteRepository repository;
	
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public SiteDtoRepositoryImpl(SiteRepository repository) {
		super(SiteDto.class, SiteDtoList.class, Site.class, repository);
		this.repository = repository;
	}
}
