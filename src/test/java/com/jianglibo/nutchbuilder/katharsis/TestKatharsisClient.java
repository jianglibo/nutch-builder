package com.jianglibo.nutchbuilder.katharsis;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;

import io.katharsis.client.KatharsisClient;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.registry.ResourceRegistry;

public class TestKatharsisClient {

	@Test
	public void t() {
		KatharsisClient kc = new KatharsisClient("http://localhost:8080/jsonapi");
		ResourceRegistry rr = kc.getModuleRegistry().getResourceRegistry();
		ResourceRepositoryV2<SiteDto, Long> taskRepo = kc.getRepositoryForType(SiteDto.class);
		assertNotNull(taskRepo);
		assertThat(rr.getResources().size(), greaterThan(0));
	}
}
