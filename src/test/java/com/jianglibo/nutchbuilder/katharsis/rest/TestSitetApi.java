package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;

import io.katharsis.resource.Document;

public class TestSitetApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Autowired
	private SiteRepository repository;
	
	@Autowired
	private CrawlCatRepository ccrepository;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		repository.deleteAll();
		ccrepository.deleteAll();
	}
	
	@Test
	public void tAddOneNoCrawlCat() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem("sitenocrawlcat", jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {

		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setProjectRoot("rj");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		String fixture = getFixture("sitepost");
		Document d = replaceRelationshipId(fixture, "id", String.valueOf(crawlCat.getId()), "data", "relationships", "crawlCat", "data");
		ResponseEntity<String> response = postItem(d, jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
	}
	
	@Test
	public void tCreateByRepo() throws IOException {
		loginAs("kkk", "ADMINISTRATOR");
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setProjectRoot("rj");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		Site site = new Site();
		site.setHomeUrl("http://a.b.c");
		site.setCrawlCat(crawlCat);
		repository.save(site);
		logout();
		ResponseEntity<String> response = getBody(jwtToken, getBaseURI());
		printme(response.getBody());
		List<SiteDto> sites = getList(response.getBody(), SiteDto.class);
		assertThat(sites.get(0).getCrawlCat().getId(), equalTo(crawlCat.getId()));
		
		response = getBody(jwtToken, getItemUrl(site.getId()));
		printme(response.getBody());
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.SITE;
	}

}
