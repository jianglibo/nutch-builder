package com.jianglibo.nutchbuilder.jwt;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.katharsis.dto.LoginAttemptDto;
import com.jianglibo.nutchbuilder.katharsis.rest.TestLoginAttemptApi;

public class TestJwtBasicFilter extends KatharsisBase {
	
	/**
	 * @see TestLoginAttemptApi to obtains a valid token.
	 */
	private String bareHead = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcmluY2lwYWwiLCJnZW5kZXIiOiJGRU1BTEUiLCJtb2JpbGUiOiIxMjM0NTY3ODkwMTIiLCJpc3MiOiJqaWFuZ2xpYm8iLCJpZCI6IjEzMTA3MiIsImV4cCI6MTQ5NDIxMzI3MCwiZW1haWwiOiJhZG1pbkBsb2NhbGhvc3QuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTklTVFJBVE9SIiwiUk9MRV9VU0VSIl0sInVzZXJuYW1lIjoiYWRtaW4ifQ.xypHHDsVH-gFjAptlvKF0-pqgy4sP06Eoh-ylueOmo8";
	
	//	Authorization: Bearer <token>
	
	@Test
	public void t() throws IOException {
		HttpHeaders requestHeaders = getCsrfHeader();
		requestHeaders.set("Authorization", "Bearer " + bareHead);
		HttpEntity<String> entity = new HttpEntity<String>(getFixture(JsonApiResourceNames.ROLE), requestHeaders);
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseURI(), entity, String.class);
		writeDto(response, "jwt", "expiredresponse");
		
		String jwt = getAjwt();
		requestHeaders = getCsrfHeader();
		requestHeaders.set("Authorization", "Bearer " + jwt);
		entity = new HttpEntity<String>(getFixture(JsonApiResourceNames.ROLE), requestHeaders);
		response = restTemplate.postForEntity(getBaseURI(JsonApiResourceNames.ROLE), entity, String.class);
		
		// cause of issued token's expiration is less then 5 minutes, it always return a new token.
		String rjwt = response.getHeaders().getFirst(JwtUtil.REFRESH_HEADER_NAME); 
		assertNotNull(rjwt);
		assertThat(rjwt.length(), greaterThan(30));
		assertNotEquals(jwt, rjwt);
	}
	
	protected String getAjwt() throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixtureWithExplicitName("loginAttemptRightCredential"));
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseURI(JsonApiResourceNames.LOGIN_ATTEMPT), request, String.class);
		LoginAttemptDto ld = getOne(response, LoginAttemptDto.class);
		String s = ld.getJwtToken();
		return s;
	}
	

	@Override
	protected String getResourceName() {
		return null;
	}

}
