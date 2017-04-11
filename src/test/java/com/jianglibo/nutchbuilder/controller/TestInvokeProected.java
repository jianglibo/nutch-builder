package com.jianglibo.nutchbuilder.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.jianglibo.nutchbuilder.KatharsisBase;


public class TestInvokeProected extends KatharsisBase {
	
	@Test
	public void t() {
		ResponseEntity<String> response = restTemplate.getForEntity(domainName + "/ppp", String.class);
		assertThat(response.getBody(), equalTo("denied"));
	}

	@Override
	protected String getResourceName() {
		return null;
	}


}
