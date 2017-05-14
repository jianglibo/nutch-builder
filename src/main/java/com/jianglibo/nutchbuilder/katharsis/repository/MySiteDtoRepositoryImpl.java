package com.jianglibo.nutchbuilder.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.domain.MySite;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.facade.BootUserFacadeRepository;
import com.jianglibo.nutchbuilder.facade.CrawlCatFacadeRepository;
import com.jianglibo.nutchbuilder.facade.MySiteFacadeRepository;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.repository.MySiteDtoRepository.MySiteDtoList;
import com.jianglibo.nutchbuilder.util.HomepageSplitter;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil;
import com.jianglibo.nutchbuilder.util.QuerySpecUtil.RelationQuery;
import com.jianglibo.nutchbuilder.util.SecurityUtil;

import io.katharsis.queryspec.QuerySpec;

@Component
public class MySiteDtoRepositoryImpl  extends DtoRepositoryBase<MySiteDto, MySiteDtoList, MySite, MySiteFacadeRepository> implements MySiteDtoRepository {
	
	private final SiteFacadeRepository siteRepository;
	
	private final BootUserFacadeRepository bootUserRepository;
	
	private final CrawlCatFacadeRepository crawlCatFacadeRepository;
	
	@Autowired
	public MySiteDtoRepositoryImpl(MySiteFacadeRepository repository, SiteFacadeRepository siteRepository,BootUserFacadeRepository bootUserRepository, CrawlCatFacadeRepository crawlCatFacadeRepository) {
		super(MySiteDto.class, MySiteDtoList.class, MySite.class, repository);
		this.siteRepository = siteRepository;
		this.bootUserRepository = bootUserRepository;
		this.crawlCatFacadeRepository = crawlCatFacadeRepository;
	}
	
	@Override
	public MySiteDto findOne(Long id, QuerySpec querySpec) {
		MySite md =  getRepository().findOne(id, false);
		MySiteDto mdo = new MySiteDto().fromEntity(md);
		mdo.setCreator(new UserDto().fromEntity(md.getCreator()));
		return mdo;
	}

	@Override
	public MySite saveToBackendRepo(MySiteDto dto, MySite entity) {
		Long uid;
		if (dto.getCreator() != null) {
			uid = dto.getCreator().getId();
		} else {
			uid = SecurityUtil.getLoginUserId();
		}
		BootUser bu = bootUserRepository.findOne(uid, true);
		entity.setCreator(bu);
		HomepageSplitter hs = new HomepageSplitter(dto.getHomepage()).split();		
		Site site = siteRepository.findByDomainName(hs.getDomainName(), true);
		if (site == null) {
			site = new Site();
			site.setProtocol(hs.getProtocol());
			site.setDomainName(hs.getDomainName());
			site.setEntryPath(hs.getEntryPath());
			CrawlCat cc = crawlCatFacadeRepository.findByName("html", true);
			site.setCrawlCat(cc);
			site = siteRepository.save(site);
		}
		entity.setSite(site);
		return getRepository().save(entity);
	}

	@Override
	protected MySiteDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}
	
	@Override
	protected MySiteDto convertToDto(MySite entity) {
		MySiteDto dto = super.convertToDto(entity);
		dto.setCreator(new UserDto().fromEntity(entity.getCreator()));
		return dto;
	}

	@Override
	protected MySiteDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("creator".equals(rq.getRelationName())) {
			List<MySite> mysites = getRepository().findMine(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countMine(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			return convertToResourceList(mysites, count);
		}
		return null;
	}
	
}
