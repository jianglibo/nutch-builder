package com.jianglibo.nutchbuilder.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.facade.SortBroker;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;
import com.jianglibo.nutchbuilder.vo.RoleNames;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class SiteFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Site, SiteRepository> implements SiteFacadeRepository {
	
	private final CrawlCatRepository ccRepository;

	@Autowired
	public SiteFacadeRepositoryImpl(SiteRepository jpaRepo, CrawlCatRepository ccRepository) {
		super(jpaRepo);
		this.ccRepository = ccRepository;
	}

	@Override
	@PreAuthorize("hasAnyRole(" + "'" + RoleNames.ROLE_ADMINISTRATOR + "'" + ",'" + RoleNames.ROLE_SITEMANAGER + "')")
	public Site findByDomainName(String homepage) {
		return getRepository().findByDomainName(homepage);
	}
	
	@Override
	@PreAuthorize("hasAnyRole(" + "'" + RoleNames.ROLE_ADMINISTRATOR + "'" + ",'" + RoleNames.ROLE_SITEMANAGER + "')")
	public List<Site> findRange(long offset, long limit, SortBroker... sortFields) {
		return super.findRange(offset, limit, sortFields);
	}

	@Override
	public List<Site> findByCrawlCat(Long crawlCatId, long offset, Long limit, SortBroker...sortBrokers) {
		return getRepository().findByCrawlCat(ccRepository.findOne(crawlCatId), getSimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByCrawlCat(Long crawlCatId) {
		return getRepository().countByCrawlCat(ccRepository.findOne(crawlCatId));
	}
}
