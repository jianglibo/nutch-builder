package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.Site;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;

public class TestUserApi  extends KatharsisBase {
	
	private static final String USER_T1 = "USER_T1";
	
	private String jwtToken;
	
	@Autowired
	private BootUserRepository userRepository;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		BootUser admin1 = userRepository.findByName(USER_T1);
		if (admin1 != null) {
			loginAsAdmin();
			userRepository.delete(admin1);
			logout();
		}
		long c= userRepository.count();
		ResponseEntity<String> response = postItem(jwtToken);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		assertThat(userRepository.count() - 1, equalTo(c));
		UserDto newUser = getOne(response.getBody(), UserDto.class);
		assertThat(newUser.getName(), equalTo(USER_T1));
		assertTrue("password should be empty.", newUser.getPassword() == null || newUser.getPassword().isEmpty());
		assertThat("id should great than 0.", newUser.getId(), greaterThan(0L));
		response = requestForBody(jwtToken, getItemUrl(newUser.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		response = deleteByExchange(jwtToken, getItemUrl(newUser.getId()));
		assertThat(userRepository.count(), equalTo(c));
	}
	
//	@Test
//	public void tAddSiteToUser() throws IOException {
//		BootUser bu = loginAsAdmin();
//		response = addRelation("mysites-postcontent", "mysites", bu.getId(), jwtToken);
//		writeDto(response, getResourceName(), "add-mysites-relation-result");
//	}
	
	@Test
	public void getMysitesRelationsSelf() throws Exception {
		BootUser bu = loginAsAdmin();
		Site site = createSite();
		IntStream.range(0, 10).forEach(i -> {
			createMySite(bu, site);
		});
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/relationships/mysites");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getmysitesrelations-self");
		List<MySiteDto> mysites = getList(response, MySiteDto.class);
		assertThat(mysites.size(), equalTo(10));
		
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/relationships/mysites?page[limit]=1&page[offset]=0");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getmysitesrelations-self");
		mysites = getList(response, MySiteDto.class);
		assertThat(mysites.size(), equalTo(1));
	}
	
	@Test
	public void getMysitesRelationsRelated() throws Exception {
		BootUser bu = loginAsAdmin();
		Site site = createSite();
		IntStream.range(0, 10).forEach(i -> {
			createMySite(bu, site);
		});
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/mysites");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getmysitesrelations-related");
		List<MySiteDto> mysites = getList(response, MySiteDto.class);
		assertThat(mysites.size(), equalTo(10));
	}
	
	@Test
	public void tGet() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
	}
	
	@Test
	public void tDeleteSelf() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = userRepository.findByName("admin");
		ResponseEntity<String> response = deleteByExchange(jwtToken, getItemUrl(bu.getId()));
		getErrors(response);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
