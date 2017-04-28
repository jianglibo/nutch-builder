package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.katharsis.dto.CrawlCatDto;
import com.jianglibo.nutchbuilder.util.JsonApiUrlBuilder;

public class TestCrawlCatApi  extends KatharsisBase {
	
	private String jwtToken;
	
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		deleteAllSitesAndCrawlCats();
	}
	
	@Test(expected=AccessDeniedException.class)
	public void byRepoUser() throws IOException {
		loginAs("user", "USER");
		CrawlCat cc = new CrawlCat();
		cc.setName("a");
		cc.setProjectRoot("b");
		ccrepository.save(cc);
	}
	
	@Test(expected=AuthenticationCredentialsNotFoundException.class)
	public void byRepoAdmin() {
		CrawlCat cc = new CrawlCat();
		cc.setName("a");
		cc.setProjectRoot("b");
		ccrepository.save(cc);
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem("crawlcat", jwtToken);
		printme(response.getBody());
		CrawlCatDto ccd = getOne(response, CrawlCatDto.class);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		assertThat("id should great than 0.", ccd.getId(), greaterThan(0L));
		response = getBody(jwtToken, getBaseURI());
		printme(response.getBody());
		
		createSite(ccrepository.findOne(ccd.getId()));
		response = getBody(jwtToken, new JsonApiUrlBuilder(getItemUrl(ccd.getId())).withInclude("sites").build()); //include sites
		
		printme(response.getBody());
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.CRAWL_CAT;
	}

}
