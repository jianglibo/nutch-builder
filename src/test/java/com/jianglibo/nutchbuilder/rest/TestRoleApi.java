package com.jianglibo.nutchbuilder.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;

import io.katharsis.client.KatharsisClient;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;

public class TestRoleApi  extends KatharsisBase {
	
//	{"data":[{"id":"2","type":"roles","attributes":{"name":"ROLE_USER"},"links":{"self":"http://localhost:8080/jsonapi/roles/2"}},{"id":"2","type":"roles","attributes":{"name":"ROLE_USER"},"links":{"self":"http://localhost:8080/jsonapi/roles/2"}}],"links":{"first":"http://localhost:8080/jsonapi/roles/?page[limit]=20","last":"http://localhost:8080/jsonapi/roles/?page[limit]=20","next":null,"prev":null},"meta":{"totalResourceCount":2}}
	
	@Test
	public void exampleTest() {
		String body = restTemplate.getForObject(getBaseURI(), String.class);
		printme(body);
		jsonAssertUtil();
		assertThat(body, equalTo("Hello World"));
		
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
		return "roles";
	}

}
