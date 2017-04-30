package com.jianglibo.nutchbuilder.katharsis.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.facade.BootUserFacadeRepository;
import com.jianglibo.nutchbuilder.facade.CrawlCatFacadeRepository;
import com.jianglibo.nutchbuilder.facade.SiteFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.repository.SiteDtoRepository.SiteDtoList;

@Component
public class SiteDtoRepositoryImpl  extends DtoRepositoryBase<SiteDto, SiteDtoList, Site, SiteFacadeRepository> implements SiteDtoRepository {
	
	private final CrawlCatFacadeRepository ccrepository;
	
	private final BootUserFacadeRepository userRepository;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public SiteDtoRepositoryImpl(SiteFacadeRepository repository, CrawlCatFacadeRepository ccrepository, BootUserFacadeRepository userRepository) {
		super(SiteDto.class, SiteDtoList.class, Site.class, repository);
		this.ccrepository = ccrepository;
		this.userRepository = userRepository;
	}

	@Override
	public Site saveToJpaRepo(SiteDto dto, Site entity) {
		entity.setCrawlCat(ccrepository.findOne(dto.getCrawlCat().getId()));
		entity.setCreator(userRepository.findOne(dto.getCreator().getId()));
		return getRepository().save(entity);
	}
	
	@Override
	public SiteDto convertToDto(Site site) {
		SiteDto siteDto = super.convertToDto(site);
		siteDto.setCrawlCat(new CrawlCatDto().fromEntity(site.getCrawlCat()));
		siteDto.setCreator(new UserDto().fromEntity(site.getCreator()));
		return siteDto;
	}
}
