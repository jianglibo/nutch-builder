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
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.exception.AppExceptionMapper;

public class TestRoleApi  extends KatharsisBase {
	
	private List<RoleDto> originRoles;
	
	private String role1 = "ROLE_USER_T1";
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getJwtToken();
		ResponseEntity<String> response = getBody(jwtToken, getBaseURI());
		String body = response.getBody();
		printme(body);
		originRoles = getList(body, RoleDto.class);
		Optional<RoleDto> ro = originRoles.stream().filter(r -> role1.equals(r.getName())).findAny(); 
		if (ro.isPresent()) {
			deleteByExchange(jwtToken, getItemUrl(ro.get().getId()));
			originRoles.remove(ro.get());
		}
	}
	
	
	@Test
	public void tAddOneNoName() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem("rolenoname", jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		RoleDto newRole = getOne(response.getBody(), RoleDto.class);
		assertThat(newRole.getName(), equalTo(role1));
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem("role", jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		RoleDto newRole = getOne(response.getBody(), RoleDto.class);
		assertThat(newRole.getName(), equalTo(role1));
		// again
		response = postItem("role", jwtToken);
		String body = response.getBody();
		printme(body);
		assertThat(response.getStatusCodeValue(), equalTo(AppExceptionMapper.APP_ERROR_STATUS_CODE));
		deleteByExchange(jwtToken, getItemUrl(newRole.getId()));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.ROLE;
	}

}
