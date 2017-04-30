package com.jianglibo.nutchbuilder.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.CrawlFrequency;
import com.jianglibo.nutchbuilder.facade.CrawlFrequencyFacadeRepository;
import com.jianglibo.nutchbuilder.repository.CrawlFrequencyRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class CrawlFrequencyFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<CrawlFrequency, CrawlFrequencyRepository> implements CrawlFrequencyFacadeRepository {

	public CrawlFrequencyFacadeRepositoryImpl(CrawlFrequencyRepository jpaRepo) {
		super(jpaRepo);
	}


}
