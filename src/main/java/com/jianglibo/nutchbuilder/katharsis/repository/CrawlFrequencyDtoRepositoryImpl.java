package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlFrequency;
import com.jianglibo.nutchbuilder.facade.CrawlFrequencyFacadeRepository;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.repository.CrawlFrequencyDtoRepository.CrawlFrequencyDtoList;


@Component
public class CrawlFrequencyDtoRepositoryImpl  extends DtoRepositoryBase<CrawlFrequencyDto, CrawlFrequencyDtoList, CrawlFrequency> implements CrawlFrequencyDtoRepository {
	
	private final CrawlFrequencyFacadeRepository repository;
	
	private final SiteFacadeRepository siteRepository;
	
	@Autowired
	public CrawlFrequencyDtoRepositoryImpl(CrawlFrequencyFacadeRepository repository, SiteFacadeRepository siteRepository) {
		super(CrawlFrequencyDto.class, CrawlFrequencyDtoList.class, CrawlFrequency.class, repository);
		this.repository = repository;
		this.siteRepository = siteRepository;
	}
	
	@Override
	public CrawlFrequency saveToJpaRepo(CrawlFrequencyDto dto, CrawlFrequency entity) {
		SiteDto sd = dto.getSite();
		entity.setSite(siteRepository.findOne(sd.getId()));
		return repository.save(entity);
	}
}