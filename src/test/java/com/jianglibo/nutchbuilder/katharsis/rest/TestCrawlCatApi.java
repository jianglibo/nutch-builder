package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;

public class TestCrawlCatApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Autowired
	private CrawlCatRepository repository;
	
	@Autowired
	private SiteRepository siteRepository;

	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		siteRepository.deleteAll();
		repository.deleteAll();
	}
	
	@Test(expected=AccessDeniedException.class)
	public void byRepoUser() throws IOException {
		loginAs("user", "USER");
		CrawlCat cc = new CrawlCat();
		cc.setName("a");
		cc.setProjectRoot("b");
		repository.save(cc);
	}
	
	@Test(expected=AuthenticationCredentialsNotFoundException.class)
	public void byRepoAdmin() {
		CrawlCat cc = new CrawlCat();
		cc.setName("a");
		cc.setProjectRoot("b");
		repository.save(cc);
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem("crawlcat", jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		Long id = getResponseIdLong(response);
		assertThat("id should great than 0.", id, greaterThan(0L));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.CRAWL_CAT;
	}

}
