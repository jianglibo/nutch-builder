package com.jianglibo.nutchbuilder;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.config.StatelessCSRFFilter;
import com.jianglibo.nutchbuilder.constant.AppErrorCodes;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.domain.MySite;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.domain.Site.SiteProtocol;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;
import com.jianglibo.nutchbuilder.repository.MySiteRepository;
import com.jianglibo.nutchbuilder.repository.SiteRepository;
import com.jianglibo.nutchbuilder.vo.RoleNames;

import io.katharsis.client.KatharsisClient;
import io.katharsis.core.internal.boot.KatharsisBoot;
import io.katharsis.errorhandling.ErrorData;
import io.katharsis.resource.Document;

public abstract class KatharsisBase extends Tbase {
	
	private static String mt = "application/vnd.api+json;charset=UTF-8";
	
	private static Path dtosPath = Paths.get("fixturesingit", "dtos");
	
	protected static class ActionNames {
		public static String POST_RESULT = "postresult";
		public static String POST_ERROR = "posterror";
		public static String GET_LIST = "getlist";
		public static String GET_ONE = "getone";
		public static String GET_ONE_INCLUDE = "getoneinclude";
		public static String GET_RELATION_SELF = "getrelation-self";
		public static String GET_RELATION_RELATED = "getrelation-related";
	}
	
	protected ResponseEntity<String> response;
	
	@Autowired
	@Qualifier("indentOm")
	protected ObjectMapper indentOm;
	
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
	
	public List<ErrorData> getErrors(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
		Document d = toDocument(response.getBody());
		return d.getErrors();
	}
	
	public void assertErrors(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
		Document d = toDocument(response.getBody());
		assertThat(d.getErrors().size(), greaterThan(0));
	}
	
	public void assertAccessDenied(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
		ErrorData ed = getErrorSingle(response);
		assertThat(ed.getCode(), equalTo(AppErrorCodes.ACCESS_DENIED));
	}
	
	public void assertData(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
		Document d = toDocument(response.getBody());
		assertTrue(d.getData().isPresent());
	}

	
	public ErrorData getErrorSingle(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
		Document d = toDocument(response.getBody());
		return d.getErrors().get(0);
	}
	
	public Long getNormalUserId() {
		return bootUserRepo.findByName("user").getId();
	}
	
	public void deleteAllUsers() {
		mySiteRepository.deleteAll();
		bootUserRepo.deleteAll();
	}
	
	public void deleteAllSitesAndCrawlCats() {
		List<MySite> mysites = mySiteRepository.findAll();
		mySiteRepository.delete(mysites);
		List<Site> sites = siteRepository.findAll();
		siteRepository.delete(sites);
		List<CrawlCat> ccc = ccrepository.findAll();
		ccrepository.delete(ccc);
	}
	
	public void writeDto(String content, String resourceName, String action) {
		try {
			Map<String, Object> v = indentOm.readValue(content.getBytes(StandardCharsets.UTF_8), Map.class);
			content = indentOm.writeValueAsString(v);
			Files.write(dtosPath.resolve(resourceName + "-" + action + ".json"), content.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeDto(ResponseEntity<String> re, String resourceName, String action) {
		writeDto(re.getBody(), resourceName, action);
	}
	
	public Site createSite() {
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("c" + new Random().nextLong());
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		Site site = new Site();
		site.setProtocol(SiteProtocol.HTTP);
		site.setDomainName("a" + new Random().nextLong() + ".b.com");
		site.setCrawlCat(crawlCat);
		site = siteRepository.save(site);
		logout();
		return site;
	}
	
	
	public Site createSite(CrawlCat crawlCat) {
		Site site = new Site();
		site.setProtocol(SiteProtocol.HTTP);
		site.setDomainName("a.b.com");
		site.setCrawlCat(crawlCat);
		site = siteRepository.save(site);
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
	
	public MySite createMySite(BootUser bu, Site site) {
		MySite mysite = new MySite();
		mysite.setHomepage("http://hp" + new Random().nextLong() + ".c.com");
		mysite.setSite(site);
		mysite.setCbsecret("secret");
		mysite.setCburl("aaa");
		mysite.setCburlVerified(true);
		mysite.setCreator(bu);
		return mySiteRepository.save(mysite);
	}
	
	public ResponseEntity<String> requestForBody(String jwtToken, String url) throws IOException {
		HttpHeaders hds = getAuthorizationHaders(jwtToken);
		HttpEntity<String> request = new HttpEntity<>(hds);
		
		return restTemplate.exchange(
		        url,
		        HttpMethod.GET, request, String.class);
	}
	
	public ResponseEntity<String> addRelation(String fixtureName,String relationName,Long itemId, String jwtToken) throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixtureWithExplicitName(fixtureName), getAuthorizationHaders(jwtToken));
		return restTemplate.postForEntity(getItemUrl(itemId) + "/relationships/" + relationName, request, String.class);
	}

	
	public ResponseEntity<String> postItemWithExplicitFixtures(String fixtureName, String jwtToken) throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixtureWithExplicitName(fixtureName), getAuthorizationHaders(jwtToken));
		return restTemplate.postForEntity(getBaseURI(), request, String.class);
	}
	
	public ResponseEntity<String> postItem(String jwtToken) throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixture(getResourceName()), getAuthorizationHaders(jwtToken));
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
		return getJwtToken("loginAdmin", RoleNames.ROLE_ADMINISTRATOR);
	}
	
	public String getNormalJwtToken() throws IOException {
		return getJwtToken("loginUser");
	}
	
	public String getJwtToken(String fixtrue, String...roles) throws IOException {
		String c = getFixtureWithExplicitName(fixtrue);
		
		Map<String, Object> m = kboot.getObjectMapper().readValue(c, Map.class);
		m = (Map<String, Object>) m.get("data");
		m = (Map<String, Object>) m.get("attributes");
		String username = (String) m.get("username");
		String password = (String) m.get("password");
		createBootUser(username, password, roles);
		
		HttpEntity<String> request = new HttpEntity<String>(c);
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

	
	public String getFixtureWithExplicitName(String fname) throws IOException {
		return new String(Files.readAllBytes(Paths.get("fixturesingit", "dtos", fname + ".json")));
	}
	
	public String getFixture(String resourceName) throws IOException {
		return new String(Files.readAllBytes(Paths.get("fixturesingit", "dtos", resourceName  + "-postcontent.json")));
	}

	
	protected Document replaceRelationshipId(String origin,String key, String value, String...paths) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(replaceRelationshipIdReturnString(origin, key, value, paths), Document.class);
	}
	
	protected Document replaceRelationshipId(String origin,String relationName, Long id) throws JsonParseException, JsonMappingException, IOException {
		String ns = replaceRelationshipIdReturnString(origin, relationName, id);
		return objectMapper.readValue(ns, Document.class);
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
	
	private String getRelationships(String content, String resoureName, boolean self) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(content, Map.class);
		Map<String, Object> dest = m;
		String[] paths = new String[]{"data", "relationships", resoureName, "links"};
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		if (self) {
			return (String) dest.get("self");
		} else {
			return (String) dest.get("related");
		}
		
	}
	
	protected String getRelationshipsSelf(String content, String resoureName) throws JsonParseException, JsonMappingException, IOException {
		return getRelationships(content, resoureName, true);
	}
	protected String getRelationshipsRelated(String content, String resoureName) throws JsonParseException, JsonMappingException, IOException {
		return getRelationships(content, resoureName, false);
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

//	public ResultActions getList() throws Exception {
//		String s = getBaseURI();
//        MockHttpServletRequestBuilder rhrb = get(s);
//        ResultActions ra = mvc.perform(rhrb//
//                .contentType(mt)//
//                .accept(mt));
//        return ra;
//	}
	
	/**
	 * 
	 * @param response
	 * @param relationName
	 * @param keys
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void verifyRelationships(ResponseEntity<String> response, String...relationNames) throws JsonParseException, JsonMappingException, IOException {
		verifyAllKeys(response, relationNames, new String[]{"data", "relationships"});
	}
	
	public void verifyAllKeys(ResponseEntity<String> response, String[] keys, String...paths) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(response.getBody(), Map.class);
		Map<String, Object> dest = m;
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		assertThat(dest.keySet(), containsInAnyOrder(keys));
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
	
	public void verifyOneKey(ResponseEntity<String> response, String key, String...paths) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> m = objectMapper.readValue(response.getBody(), Map.class);
		Map<String, Object> dest = m;
		for(String seg : paths) {
			dest = (Map<String, Object>) dest.get(seg);
		}
		assertTrue("should contains key: " + key, dest.containsKey(key));
	}

}
