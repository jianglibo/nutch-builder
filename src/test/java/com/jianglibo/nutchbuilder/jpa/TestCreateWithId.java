package com.jianglibo.nutchbuilder.jpa;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.nutchbuilder.KatharsisBase;
import com.jianglibo.nutchbuilder.domain.CrawlCat;

public class TestCreateWithId extends KatharsisBase {
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllSitesAndCrawlCats();
	}

	/**
	 * jpa will change id value even if you assigned one.
	 */
	
	@Test
	public void t() {
		LongStream.range(1, 4).forEach(i -> {
			CrawlCat crawlCat = new CrawlCat();
			crawlCat.setId(i);
			crawlCat.setName("acc" + i);
			crawlCat.setDescription("dd");
			crawlCat = ccrepository.save(crawlCat);
		});
		
		List<Long> ids = ccrepository.findAll().stream().map(cc -> cc.getId()).collect(Collectors.toList());
		assertFalse(ids.contains(1L));
		assertFalse(ids.contains(2L));
		assertFalse(ids.contains(3L));
	}

	@Override
	protected String getResourceName() {
		return null;
	}

}
