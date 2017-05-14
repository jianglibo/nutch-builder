package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.resource.Document;

public class TestLoginAttemptApi  extends KatharsisBase {
	
	
	@Test
	public void tWrongCredential() throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixtureWithExplicitName("loginAttemptWrongCredential"));
		response = restTemplate.postForEntity(getBaseURI(), request, String.class);
		String body = response.getBody();
		writeDto(response, getResourceName(), "failed");
		
		Document d =  kboot.getObjectMapper().readValue(body, Document.class);
		List<ErrorData> eds = d.getErrors();
		assertThat(eds.size(), equalTo(1));
		
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	public void tRightCredential() throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixtureWithExplicitName("loginAttemptRightCredential"));
		response = restTemplate.postForEntity(getBaseURI(), request, String.class);
		String body = response.getBody();
		writeDto(response, getResourceName(), "success");
		Document d =  kboot.getObjectMapper().readValue(body, Document.class);
		List<ErrorData> eds = d.getErrors();
		assertNull(eds);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		verifyOneKey(response, "jwtToken", "data", "attributes");
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.LOGIN_ATTEMPT;
	}

}
