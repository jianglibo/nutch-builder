package com.jianglibo.nutchbuilder.jwt;

import java.io.IOException;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.jianglibo.nutchbuilder.KatharsisBase;

public class TestJwtBasicFilter extends KatharsisBase {
	
	private String bareHead = "eyJhbGciOiJIUzI1NiJ9.eyJtb2JpbGUiOiIxMjM0NTY3ODkwMTIiLCJpc3MiOiJqaWFuZ2xpYm8iLCJpZCI6IjEiLCJleHAiOjE0OTEyMzIzMzgsImVtYWlsIjoiYWRtaW5AbG9jYWxob3N0LmNvbSIsImF1dGhvcml0aWVzIjpbXSwidXNlcm5hbWUiOiJhZG1pbiJ9.Dz-QJ3xalFP7xikPFEQYGbBH-A4Cv5fA8j5_WOSTq7M";
	
//	Authorization: Bearer <token>
	
	@Test
	public void t() throws IOException {
		HttpHeaders requestHeaders = getCsrfHeader();
		requestHeaders.set("Authorization", "Bearer " + bareHead);
		HttpEntity<String> entity = new HttpEntity<String>(getFixture("role"),requestHeaders);
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseURI(), entity, String.class);
	}

	@Override
	protected String getResourceName() {
		return null;
	}

}
