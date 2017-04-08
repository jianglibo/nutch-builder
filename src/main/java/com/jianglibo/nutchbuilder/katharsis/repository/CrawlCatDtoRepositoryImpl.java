package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;
import com.jianglibo.nutchbuilder.katharsis.repository.CrawlCatDtoRepository.CrawlCatDtoList;


@Component
public class CrawlCatDtoRepositoryImpl  extends DtoRepositoryBase<CrawlCatDto, CrawlCatDtoList, CrawlCat> implements CrawlCatDtoRepository {
	
	private final CrawlCatRepository repository;
	
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public CrawlCatDtoRepositoryImpl(CrawlCatRepository repository) {
		super(CrawlCatDto.class, CrawlCatDtoList.class, CrawlCat.class, repository);
		this.repository = repository;
	}
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles/32768 -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Delete
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"name": "test"}, "type": "roles"}}' -Method Post
}
