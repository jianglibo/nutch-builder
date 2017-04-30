package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.facade.CrawlCatFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.repository.CrawlCatDtoRepository.CrawlCatDtoList;


@Component
public class CrawlCatDtoRepositoryImpl  extends DtoRepositoryBase<CrawlCatDto, CrawlCatDtoList, CrawlCat, CrawlCatFacadeRepository> implements CrawlCatDtoRepository {
	
	@Autowired
	public CrawlCatDtoRepositoryImpl(CrawlCatFacadeRepository repository) {
		super(CrawlCatDto.class, CrawlCatDtoList.class, CrawlCat.class, repository);
	}
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles/32768 -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Delete
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"name": "test"}, "type": "roles"}}' -Method Post
}
