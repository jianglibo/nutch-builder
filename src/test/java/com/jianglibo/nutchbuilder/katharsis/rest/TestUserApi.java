package com.jianglibo.nutchbuilder.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;
import com.jianglibo.nutchbuilder.katharsis.exception.AppExceptionMapper;

public class TestUserApi  extends KatharsisBase {
	
	private List<UserDto> originUsers;
	
	private String user1 = "USER_T1";
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getJwtToken();
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
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem("user", jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		UserDto newRole = getOne(response.getBody(), UserDto.class);
		assertThat(newRole.getName(), equalTo(user1));
		// again
		response = postItem("user", jwtToken);
		String body = response.getBody();
		printme(body);
		assertThat(response.getStatusCodeValue(), equalTo(AppExceptionMapper.APP_ERROR_STATUS_CODE));
		deleteByExchange(jwtToken, getItemUrl(newRole.getId()));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
