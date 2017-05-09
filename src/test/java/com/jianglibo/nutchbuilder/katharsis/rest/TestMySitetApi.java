package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.domain.Site.SiteProtocol;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;

public class TestMySitetApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		deleteAllSitesAndCrawlCats();
	}
	
	/**
	 * Mysite has two relationships, urlfilters and creator. Both are optional on dto level, but mandatory on jpa level. It's extracted from environment, user principle for example.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		// required for site object.
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("html");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		
		Site site = new Site();
		site.setProtocol(SiteProtocol.HTTP);
		site.setDomainName("www.abc.com"); // must equals to the domainName's value in mysites-postcontent.json
		site.setCrawlCat(crawlCat);
		site = siteRepository.save(site);
		
		ResponseEntity<String> response = postItem(jwtToken);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		
		verifyRelationships(response,"creator");
		MySiteDto sd = getOne(response, MySiteDto.class);
		List<MySiteDto> sds = getList(response, MySiteDto.class);
		assertThat(sds.size(), equalTo(1));
		
		response = requestForBody(jwtToken, getItemUrl(sd.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.MY_SITE;
	}

}
