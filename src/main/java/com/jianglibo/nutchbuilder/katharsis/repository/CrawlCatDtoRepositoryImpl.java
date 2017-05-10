package com.jianglibo.nutchbuilder.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.facade.CrawlCatFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.repository.CrawlCatDtoRepository.CrawlCatDtoList;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;


@Component
public class CrawlCatDtoRepositoryImpl  extends DtoRepositoryBase<CrawlCatDto, CrawlCatDtoList, CrawlCat, CrawlCatFacadeRepository> implements CrawlCatDtoRepository {
	
	@Autowired
	public CrawlCatDtoRepositoryImpl(CrawlCatFacadeRepository repository) {
		super(CrawlCatDto.class, CrawlCatDtoList.class, CrawlCat.class, repository);
	}
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles/32768 -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Delete
//	$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/roles -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"name": "test"}, "type": "roles"}}' -Method Post

	@Override
	protected CrawlCatDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CrawlCatDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}
}
