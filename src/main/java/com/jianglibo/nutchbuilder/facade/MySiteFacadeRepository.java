package com.jianglibo.nutchbuilder.facade;

import java.util.List;

import com.jianglibo.nutchbuilder.domain.MySite;

public interface MySiteFacadeRepository extends FacadeRepositoryBase<MySite> {

	List<MySite> findMine(long userId, long offset, Long limit, SortBroker...sortBrokers);

	long countMine(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
}
