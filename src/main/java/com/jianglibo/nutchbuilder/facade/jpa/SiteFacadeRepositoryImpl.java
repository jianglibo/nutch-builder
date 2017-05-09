package com.jianglibo.nutchbuilder.facade.jpa;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;
import com.jianglibo.nutchbuilder.vo.RoleNames;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class SiteFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Site, SiteRepository> implements SiteFacadeRepository {

	public SiteFacadeRepositoryImpl(SiteRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	@PreAuthorize("hasAnyRole(" + "'" + RoleNames.ROLE_ADMINISTRATOR + "'" + ",'" + RoleNames.ROLE_SITEMANAGER + "')")
	public Site findByDomainName(String homepage) {
		return getRepository().findByDomainName(homepage);
	}

	@Override
	@PreAuthorize("hasAnyRole(" + "'" + RoleNames.ROLE_ADMINISTRATOR + "'" + ",'" + RoleNames.ROLE_SITEMANAGER + "')")
	public List<Site> findAllByOrderByUpdatedAtDesc() {
		return getRepository().findAllByOrderByUpdatedAtDesc();
	}
}
