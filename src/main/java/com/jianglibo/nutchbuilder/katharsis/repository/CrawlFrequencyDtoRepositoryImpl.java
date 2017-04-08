package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlFrequency;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.nutchbuilder.katharsis.repository.CrawlFrequencyDtoRepository.CrawlFrequencyDtoList;
import com.jianglibo.nutchbuilder.repository.CrawlFrequencyRepository;


@Component
public class CrawlFrequencyDtoRepositoryImpl  extends DtoRepositoryBase<CrawlFrequencyDto, CrawlFrequencyDtoList, CrawlFrequency> implements CrawlFrequencyDtoRepository {
	
	private final CrawlFrequencyRepository repository;
	
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public CrawlFrequencyDtoRepositoryImpl(CrawlFrequencyRepository repository) {
		super(CrawlFrequencyDto.class, CrawlFrequencyDtoList.class, CrawlFrequency.class, repository);
		this.repository = repository;
	}
}
