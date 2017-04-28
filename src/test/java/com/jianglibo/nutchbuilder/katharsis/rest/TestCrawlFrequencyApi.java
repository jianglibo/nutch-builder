package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.CrawlFrequency;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.repository.CrawlFrequencyRepository;

import io.katharsis.resource.Document;

public class TestCrawlFrequencyApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Autowired
	private CrawlFrequencyRepository fqrepository;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		deleteAllSitesAndCrawlCats();
	}
	
	@Test
	public void tPostOne() throws JsonParseException, JsonMappingException, IOException {
		Site site = createSite();
		String fixture = getFixture("crawlfrequencypost");
		Document d = replaceRelationshipId(fixture, "site", site.getId());
		
		ResponseEntity<String> response = postItem(d, jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
	}
	
	@Test
	public void tGetOneAndList() throws JsonParseException, JsonMappingException, IOException {
		Site site = createSite();
		
		loginAsAdmin();
		CrawlFrequency fq = new CrawlFrequency();
		fq.setRegex("xxx");
		fq.setSeconds(3000000);
		fq.setSite(site);
		fq = fqrepository.save(fq);
		logout();
		
		ResponseEntity<String> response = getBody(jwtToken, getItemUrl(fq.getId()));
		printme(response.getBody());
		response = getBody(jwtToken, getBaseURI());
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.OK.value()));
	}
	
	@Test(expected=AuthenticationCredentialsNotFoundException.class)
	public void tSecu() {
		Site site = createSite();
		CrawlFrequency fq = new CrawlFrequency();
		fq.setRegex("xxx");
		fq.setSeconds(3000000);
		fq.setSite(site);
		fq = fqrepository.save(fq);
	}
	

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.CRAWL_FRE;
	}

}
