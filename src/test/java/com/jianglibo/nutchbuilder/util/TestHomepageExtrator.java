package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jianglibo.nutchbuilder.domain.Site.SiteProtocol;

public class TestHomepageExtrator {

	@Test
	public void tOk() {
		HomepageSplitter hs = new HomepageSplitter("http://www.abc.com").split();
		assertThat(hs.getDomainName(), equalTo("www.abc.com"));
		assertThat(hs.getEntryPath(), equalTo("/"));
		assertThat(hs.getProtocol(), equalTo(SiteProtocol.HTTP));
		hs.concat();
		assertThat(hs.getHomepage(), equalTo("http://www.abc.com/"));
		
		hs = new HomepageSplitter("https://www.abc.com/").split();
		assertThat(hs.getDomainName(), equalTo("www.abc.com"));
		assertThat(hs.getEntryPath(), equalTo("/"));
		assertThat(hs.getProtocol(), equalTo(SiteProtocol.HTTPS));
		hs.concat();
		assertThat(hs.getHomepage(), equalTo("https://www.abc.com/"));
	}
	
	@Test
	public void tf1() {
		HomepageSplitter hs = new HomepageSplitter("htt://www.abc.com").split();
		assertNull(hs.getDomainName());
		assertNull(hs.getProtocol());
		assertNull(hs.getEntryPath());
	}
}
