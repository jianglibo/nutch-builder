package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

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
import com.jianglibo.nutchbuilder.domain.UrlFilter;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;
import com.jianglibo.nutchbuilder.repository.UrlFilterRepository;
import com.jianglibo.nutchbuilder.vo.RoleNames;

import io.katharsis.resource.Document;

public class TestUrlFilterApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Autowired
	private SiteRepository repository;
	
	@Autowired
	private CrawlCatRepository ccrepository;
	
	@Autowired
	private UrlFilterRepository urlrepository;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		repository.deleteAll();
		ccrepository.deleteAll();
	}
	
	@Test
	public void tPostOne() throws JsonParseException, JsonMappingException, IOException {
		loginAs("kkk", RoleNames.ROLE_ADMINISTRATOR);
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setProjectRoot("rj");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		Site site = new Site();
		site.setHomeUrl("http://a.b.c");
		site.setCrawlCat(crawlCat);
		site = repository.save(site);
		logout();
		
		String fixture = getFixture("urlfilterpost");
		Document d = replaceRelationshipId(fixture, "id", String.valueOf(site.getId()), "data", "attributes", "site");
		
		ResponseEntity<String> response = postItem(d, jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
	}
	
	@Test
	public void tGetOneAndList() throws JsonParseException, JsonMappingException, IOException {
		loginAs("kkk", RoleNames.ROLE_ADMINISTRATOR);
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setProjectRoot("rj");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		Site site = new Site();
		site.setHomeUrl("http://a.b.c");
		site.setCrawlCat(crawlCat);
		site = repository.save(site);
		logout();
		UrlFilter ul = new UrlFilter();
		ul.setRegex("xxx");
		ul.setSite(site);
		
		ul = urlrepository.save(ul);
		
		ResponseEntity<String> response = getBody(jwtToken, getItemUrl(ul.getId()));
		printme(response.getBody());
		response = getBody(jwtToken, getBaseURI());
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.OK.value()));
	}
	

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.URL_FILTER;
	}

}
