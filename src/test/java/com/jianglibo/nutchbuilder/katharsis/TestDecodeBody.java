package com.jianglibo.nutchbuilder.katharsis;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.client.internal.ClientDocumentMapper;
import io.katharsis.core.internal.boot.KatharsisBoot;
import io.katharsis.resource.Document;

public class TestDecodeBody extends KatharsisBase {
	
	private String doc = "{\"data\":{\"id\":\"1\",\"type\":\"users\",\"attributes\":{\"gender\":\"FEMALE\",\"displayName\":\"admin\",\"credentialsNonExpired\":true,\"roles\":[{\"id\":2,\"name\":\"ROLE_USER\",\"createdAt\":1488630520879},{\"id\":1,\"name\":\"ROLE_ABC\",\"createdAt\":1488630520804}],\"mobile\":\"123456789012\",\"mobileVerified\":false,\"avatar\":null,\"enabled\":true,\"emailVerified\":true,\"name\":\"admin\",\"accountNonExpired\":true,\"email\":\"admin@localhost.com\",\"accountNonLocked\":true},\"links\":{\"self\":\"http://localhost:88/jsonapi/users/1\"}}}";
	
	@Autowired
	private KatharsisBoot kboot;
	
	@Test
	public void t() throws JsonParseException, JsonMappingException, IOException {
	}

	@Override
	protected String getResourceName() {
		return null;
	}

}
