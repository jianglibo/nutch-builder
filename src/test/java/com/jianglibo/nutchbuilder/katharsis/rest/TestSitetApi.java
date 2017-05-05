package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
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
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.resource.Document;

public class TestSitetApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		deleteAllSitesAndCrawlCats();
	}
	
	@Test
	public void tAddOneNoCrawlCat() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem("sitenocrawlcat", jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		loginAsAdmin();
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		logout();
		
		String fixture = getFixture("sitepost");
		String fx1 = replaceRelationshipIdReturnString(fixture, "crawlCat", crawlCat.getId());
		BootUser bu = createBootUserPrincipal("uuu", null);
		Document d = replaceRelationshipId(fx1, "creator", bu.getId());
		ResponseEntity<String> response = postItem(d, jwtToken);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		verifyRelationshipsKeys(response,"urlfilters", "links");
		SiteDto sd = getOne(response, SiteDto.class);
		List<SiteDto> sds = getList(response, SiteDto.class);
		assertThat(sds.size(), equalTo(1));
		printme(response.getBody());
		response = getBody(jwtToken, getItemUrl(sd.getId()));
		assertNull(sd.getCrawlCat());
		printme(response.getBody());
	}
	
	@Test
	public void tCreateByRepo() throws IOException {
		loginAsAdmin();
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		Site site = new Site();
		site.setCrawlCat(crawlCat);
		siteRepository.save(site);
		logout();
		ResponseEntity<String> response = getBody(jwtToken, getBaseURI());
		printme(response.getBody());
		
		response = getBody(jwtToken, getItemUrl(site.getId()));
		SiteDto siteDto = getOne(response, SiteDto.class);
		assertThat(siteDto.getId(), equalTo(site.getId()));
		assertNull(siteDto.getCrawlCat());
		printme(response.getBody());
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.SITE;
	}

}
