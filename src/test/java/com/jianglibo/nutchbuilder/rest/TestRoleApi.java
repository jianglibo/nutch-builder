package com.jianglibo.nutchbuilder.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.config.JsonApiResourceNames;
import com.jianglibo.nutchbuilder.json.exception.AppExceptionMapper;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;

public class TestRoleApi  extends KatharsisBase {
	
	private List<RoleDto> originRoles;
	
	private String role1 = "ROLE_USER_T1";
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		String body = restTemplate.getForObject(getBaseURI(), String.class);
		originRoles = getList(body, RoleDto.class);
		Optional<RoleDto> ro = originRoles.stream().filter(r -> role1.equals(r.getName())).findAny(); 
		if (ro.isPresent()) {
			deleteByExchange(getItemUrl(ro.get().getId()));
			originRoles.remove(ro.get());
		}
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<String> request = new HttpEntity<String>(getFixture("role"));
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseURI(), request, String.class);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		RoleDto newRole = getOne(response.getBody(), RoleDto.class);
		assertThat(newRole.getName(), equalTo(role1));
		// again
		response = restTemplate.postForEntity(getBaseURI(), request, String.class);
		assertThat(response.getStatusCodeValue(), equalTo(AppExceptionMapper.APP_ERROR_STATUS_CODE));
		deleteByExchange(getItemUrl(newRole.getId()));
	}
    
//	@Test
//	public void tk() {
//		KatharsisClient client = new KatharsisClient(getKatharsisBase());
//		ResourceRepositoryV2<RoleDto, Long> taskRepo = client.getRepositoryForType(RoleDto.class);
//		List<RoleDto> tasks = taskRepo.findAll(new QuerySpec(RoleDto.class));
//		assertThat(tasks.size(), equalTo(2));
//	}
	
	
//    @Test
//    public void tAnonymousState() throws Exception {
//    	getList().andDo(new ResultHandler() {
//			@Override
//			public void handle(MvcResult result) throws Exception {
//                MockHttpServletResponse response = result.getResponse();
//                String c = response.getContentAsString();
//                printme(c);
//			}
//		}).andExpect(status().is2xxSuccessful());
//    }
//    	

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.ROLE;
	}

}
