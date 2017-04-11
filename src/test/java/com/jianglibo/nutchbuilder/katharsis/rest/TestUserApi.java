package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;
import com.jianglibo.nutchbuilder.vo.RoleNames;

public class TestUserApi  extends KatharsisBase {
	
	private List<UserDto> originUsers;
	
	private String user1 = "USER_T1";
	
	private String jwtToken;
	
	@Autowired
	private BootUserRepository userRepository;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		ResponseEntity<String> response = getBody(jwtToken, getBaseURI());
		String body = response.getBody();
		printme(body);
		originUsers = getList(body, UserDto.class);
		Optional<UserDto> uo = originUsers.stream().filter(u -> user1.equals(u.getName())).findAny(); 
		if (uo.isPresent()) {
			deleteByExchange(jwtToken, getItemUrl(uo.get().getId()));
			originUsers.remove(uo.get());
		}
	}
	
//	@Test(expected=AccessDeniedException.class)
//	public void deleteMyself() {
//		loginAs("kkk", RoleNames.ROLE_ADMINISTRATOR);
//		BootUser bu = userRepository.findByName("kkk");
//		userRepository.delete(bu.getId());
//	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		long c= userRepository.count();
		ResponseEntity<String> response = postItem("userwithroles", jwtToken);
		printme(response.getBody());
		assertThat(userRepository.count() - 1, equalTo(c));
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		UserDto newUser = getOne(response.getBody(), UserDto.class);
		assertThat(newUser.getName(), equalTo(user1));
		assertTrue("password should be empty.", newUser.getPassword() == null || newUser.getPassword().isEmpty());
		assertThat("id should great than 0.", newUser.getId(), greaterThan(0L));
		deleteByExchange(jwtToken, getItemUrl(newUser.getId()));
		assertThat(userRepository.count(), equalTo(c));
	}
	
	@Test
	public void tDeleteSelf() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = userRepository.findByName("admin");
		ResponseEntity<String> response = deleteByExchange(jwtToken, getItemUrl(bu.getId()));
		verifyAllKeys(response, new String[]{"errors"});
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
