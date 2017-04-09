package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlFrequency;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.repository.CrawlFrequencyDtoRepository.CrawlFrequencyDtoList;
import com.jianglibo.nutchbuilder.repository.CrawlFrequencyRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;


@Component
public class CrawlFrequencyDtoRepositoryImpl  extends DtoRepositoryBase<CrawlFrequencyDto, CrawlFrequencyDtoList, CrawlFrequency> implements CrawlFrequencyDtoRepository {
	
	private final CrawlFrequencyRepository repository;
	
	private final SiteRepository siteRepository;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public CrawlFrequencyDtoRepositoryImpl(CrawlFrequencyRepository repository, SiteRepository siteRepository) {
		super(CrawlFrequencyDto.class, CrawlFrequencyDtoList.class, CrawlFrequency.class, repository);
		this.repository = repository;
		this.siteRepository = siteRepository;
	}
	
	@Override
	public CrawlFrequency saveToJpaRepo(CrawlFrequencyDto dto, CrawlFrequency entity) {
//		SiteDto sd = dto.getSite();
//		entity.setSite(siteRepository.findOne(sd.getId()));
		return repository.save(entity);
	}
}
