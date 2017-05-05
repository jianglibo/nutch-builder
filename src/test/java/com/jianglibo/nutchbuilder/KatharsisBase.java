package com.jianglibo.nutchbuilder;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.domain.MySite;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.domain.Site.SiteProtocol;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;
import com.jianglibo.nutchbuilder.repository.MySiteRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;

import io.katharsis.client.KatharsisClient;
import io.katharsis.core.internal.boot.KatharsisBoot;
import io.katharsis.resource.Document;

public abstract class KatharsisBase extends Tbase {
	
	private static String mt = "application/vnd.api+json;charset=UTF-8";
	
	@Autowired
	protected KatharsisBoot kboot;
	
	@Autowired
	protected SiteRepository siteRepository;
	
	@Autowired
	protected MySiteRepository mySiteRepository;

	
	@Autowired
	protected CrawlCatRepository ccrepository;

	
	@Autowired
	protected KatharsisClient katharsisClient;
	
	@Value("${katharsis.domainName}")
	protected String domainName;
	
	@Value("${katharsis.pathPrefix}")
	private String pathPrefix;

	@Value("${katharsis.default-page-limit}")
	private String pageSize;
	
	public void deleteAllSitesAndCrawlCats() {
		List<Site> sites = siteRepository.findAll();
		siteRepository.delete(sites);
		List<CrawlCat> ccc = ccrepository.findAll();
		ccrepository.delete(ccc);
	}
	
	public Site createSite() {
		BootUser bu = loginAsAdmin();
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		Site site = new Site();
		site.setProtocol(SiteProtocol.HTTP);
		site.setDomainName("a.b.com");
		site.setCrawlCat(crawlCat);
		site = siteRepository.save(site);
		logout();
		return site;
	}
	
	public MySite createMySite() {
		BootUser bu = loginAsAdmin();
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		Site site = new Site();
		site.setProtocol(SiteProtocol.HTTP);
		site.setDomainName("a.b.com");
		site.setCrawlCat(crawlCat);
		site = siteRepository.save(site);
		
		MySite mysite = new MySite();
		mysite.setSite(site);
		mysite.setCbsecret("secret");
		mysite.setCburl("aaa");
		mysite.setCburlVerified(true);
		mysite.setCreator(bu);
		return mySiteRepository.save(mysite);
	}
	
	public Site createSite(CrawlCat crawlCat) {
		Site site = new Site();
		site.setProtocol(SiteProtocol.HTTP);
		site.setDomainName("a.b.com");
		site.setCrawlCat(crawlCat);
		site = siteRepository.save(site);
		return site;
	}
	
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
	
	
	public Document toDocument(String responseBody) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		return objectMapper.readValue(responseBody, Document.class);
	}
	
	public <T> T getOne(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		return (T) katharsisClient.getDocumentMapper().fromDocument(document, false);
	}
	
	public <T> T getOne(ResponseEntity<String> response, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		return getOne(response.getBody(), targetClass);
	}
	
	public <T> List<T> getList(String responseBody, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = kboot.getObjectMapper();
		Document document = objectMapper.readValue(responseBody, Document.class);
		return (List<T>) katharsisClient.getDocumentMapper().fromDocument(document, true);
	}
	
	public <T> List<T> getList(ResponseEntity<String> response, Class<T> targetClass) throws JsonParseException, JsonMappingException, IOException {
		return getList(response.getBody(), targetClass);
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
	
	protected String getItemUrl(String id) {
		return getBaseURI() + "/" + id;
	}

	
	public String getFixture(String fname) throws IOException {
		return new String(Files.readAllBytes(Paths.get("fixturesingit", "dtos", fname + ".json")));
	}
	
	protected Document replaceRelationshipId(String origin,String key, String value, String...paths) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(replaceRelationshipIdReturnString(origin, key, value, paths), Document.class);
	}
	
	protected Document replaceRelationshipId(String origin,String relationName, Long id) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(replaceRelationshipIdReturnString(origin, relationName, id), Document.class);
	}
	
	protected String replaceRelationshipIdReturnString(String origin,String relationName, Long id) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(origin, Map.class);
		
		Map<String, Object> dest = m;
		String[] paths = new String[]{"data", "relationships", relationName, "data"};
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		dest.put("id", id);
		return objectMapper.writeValueAsString(m);
	}


	
	protected String replaceRelationshipIdReturnString(String origin,String key, String value, String...paths) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(origin, Map.class);
		
		Map<String, Object> dest = m;
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		dest.put(key, value);
		return objectMapper.writeValueAsString(m);
	}
	
	protected Document replaceRelationshipLinkId(String origin,String relationName, String myType, Long value) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(replaceRelationshipLinkIdReturnString(origin, relationName, myType, value), Document.class);
	}

	
	protected String replaceRelationshipLinkIdReturnString(String origin,String relationName, String myType, Long value) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(origin, Map.class);
		
		Map<String, Object> dest = m;
		String[] paths = new String[]{"data", "relationships", relationName, "links"};
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		if (dest.containsKey("self")) {
			String url = (String) dest.get("self");
			url = url.replaceAll("/" + myType + "/\\d+", "/" + myType + "/" + value);
			dest.put("self", url);
		}
		
		if (dest.containsKey("related")) {
			String url = (String) dest.get("related");
			url = url.replaceAll("/" + myType + "/\\d+", "/" + myType + "/" + value);
			dest.put("related", url);
		}

		
		return objectMapper.writeValueAsString(m);
	}
	
	protected Object getDocumentProperty(String responseBody, String...keys) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(responseBody, Map.class);
		String[] segs = Arrays.copyOf(keys, keys.length - 1);
		Map<String, Object> dest = m;
		for(String seg : segs) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		
		return dest.get(keys[keys.length - 1]);
	}
	
	
	
	protected Object getDocumentProperty(ResponseEntity<String> response, String...keys) throws JsonParseException, JsonMappingException, IOException {
		return getDocumentProperty(response.getBody(), keys);
	}
	
	protected String getResponseIdString(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
		return (String) getDocumentProperty(response, "data", "id");
	}
	
	protected Long getResponseIdLong(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
		return Long.valueOf((String)getDocumentProperty(response, "data", "id"));
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
	
	public void verifyRelationshipsKeys(ResponseEntity<String> response, String relationName, String...keys) throws JsonParseException, JsonMappingException, IOException {
		verifyAllKeys(response, keys, new String[]{"data", "relationships", relationName});
	}
	
	public void verifyAllKeys(ResponseEntity<String> response, String[] keys, String...paths) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(response.getBody(), Map.class);
		Map<String, Object> dest = m;
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		assertThat(dest.keySet(), contains(keys));
	}
	
	public void verifyAnyKeys(ResponseEntity<String> response, String[] keys, String...paths) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(response.getBody(), Map.class);
		Map<String, Object> dest = m;
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		for(String k: keys) {
			assertTrue("should contains key: " + k, dest.containsKey(k));
		}
	}

}
