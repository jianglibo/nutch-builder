package com.jianglibo.nutchbuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.config.StatelessCSRFFilter;

import io.katharsis.client.internal.ClientDocumentMapper;
import io.katharsis.core.internal.boot.KatharsisBoot;
import io.katharsis.resource.Document;

public abstract class KatharsisBase extends Tbase {
	
	private static String mt = "application/vnd.api+json;charset=UTF-8";
	
	@Autowired
	protected KatharsisBoot kboot;
	
	@Value("${katharsis.domainName}")
	private String domainName;
	
	@Value("${katharsis.pathPrefix}")
	private String pathPrefix;

	@Value("${katharsis.default-page-limit}")
	private String pageSize;
	
	public ResponseEntity<String> getBody(String jwtToken, String url) throws IOException {
		HttpHeaders hds = getAuthorizationHaders(jwtToken);
		HttpEntity<String> request = new HttpEntity<>(hds);
		
		return restTemplate.exchange(
		        url,
		        HttpMethod.GET, request, String.class);
	}
	
	public ResponseEntity<String> postItem(String fixtureName, String jwtToken) throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixture(fixtureName), getAuthorizationHaders(jwtToken));
		return restTemplate.postForEntity(getBaseURI(), request, String.class);
	}
	
	public ResponseEntity<String> postItem(Document document, String jwtToken) throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(objectMapper.writeValueAsString(document), getAuthorizationHaders(jwtToken));
		return restTemplate.postForEntity(getBaseURI(), request, String.class);
	}
	
	public HttpHeaders getCsrfHeader() {
		HttpHeaders requestHeaders = new HttpHeaders();
		
		requestHeaders.add("Cookie", StatelessCSRFFilter.CSRF_TOKEN + "=123456");
		requestHeaders.add(StatelessCSRFFilter.X_CSRF_TOKEN,"123456");
		return requestHeaders;
	}
	
	public HttpHeaders getAuthorizationHaders(String jwtToken) throws IOException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", "Bearer " + jwtToken);
		return requestHeaders;
	}
	
	public String getAdminJwtToken() throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixture("loginAdmin"));
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseURI(JsonApiResourceNames.LOGIN_ATTEMPT), request, String.class);
		String body = response.getBody();
		Document d =  kboot.getObjectMapper().readValue(body, Document.class);
		return d.getSingleData().get().getAttributes().get("jwtToken").asText();
	}
	
	
	public <T> List<T> getList(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		ClientDocumentMapper documentMapper = new ClientDocumentMapper(kboot.getModuleRegistry(), objectMapper, null);
		return (List<T>) documentMapper.fromDocument(document, true);
	}
	
	
	public Document toDocument(String responseBody) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		return objectMapper.readValue(responseBody, Document.class);
	}
	
	public <T> T getOne(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		ClientDocumentMapper documentMapper = new ClientDocumentMapper(kboot.getModuleRegistry(), objectMapper, null);
		return (T) documentMapper.fromDocument(document, false);
	}
	
	public ResponseEntity<String> deleteByExchange(String jwtToken, String url) throws IOException {
		HttpHeaders hds = getAuthorizationHaders(jwtToken);
		HttpEntity<String> request = new HttpEntity<>(hds);
		return restTemplate.exchange(
				url,
		        HttpMethod.DELETE, request, String.class);
	}
	
	protected String getKatharsisBase() {
		return domainName + pathPrefix;
	}
	
	protected String getBaseURI(String resourceName) {
		return domainName + pathPrefix + "/" + resourceName;
	}

	
	protected String getBaseURI() {
		return domainName + pathPrefix + "/" + getResourceName();
	}
	
	protected String getItemUrl(long id) {
		return getBaseURI() + "/" + id;
	}
	
	public String getFixture(String fname) throws IOException {
		return new String(Files.readAllBytes(Paths.get("fixturesingit", "dtos", fname + ".json")));
	}
	
	protected Document replaceRelationshipId(String origin,String key, String value, String...segnames) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(origin, Map.class);
		
		Map<String, Object> dest = m;
		for(String seg : segnames) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		dest.put(key, value);
		return objectMapper.readValue(objectMapper.writeValueAsString(m), Document.class);
	}

	
	protected abstract String getResourceName();

	public ResultActions getList() throws Exception {
		String s = getBaseURI();
        MockHttpServletRequestBuilder rhrb = get(s);
        ResultActions ra = mvc.perform(rhrb//
                .contentType(mt)//
                .accept(mt));
        return ra;
	}
}
