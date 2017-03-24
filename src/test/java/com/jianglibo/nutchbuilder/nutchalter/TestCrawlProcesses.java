package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

import com.jianglibo.nutchbuilder.crawl.NutchJobOptionBuilder;
import com.jianglibo.nutchbuilder.nutchalter.CrawlProcesses.CrawlStepProcess;

public class TestCrawlProcesses extends StepBase {
	
	@Test
	public void tFetchOk() throws Exception {
		List<String> fetchingOptions = new NutchJobOptionBuilder("test_crawl", 3).withFetchParameterBuilder(batchId).threads(50).timeLimitFetch(200).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, fetchingOptions);
		csp.call();
		int exitCode = csp.getExitCode();
		assertThat("exitCode should be 0, this indicates new item generated ", exitCode, equalTo(0));
		assertThat("some errors should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}
	
	@Test
	public void tParseOk() throws Exception {
		List<String> parseOptions = new NutchJobOptionBuilder("test_crawl", 3).withParseParameterBuilder(batchId).skipRecords(1).startSkipping(2).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, parseOptions);
		csp.call();
		int exitCode = csp.getExitCode();
		assertThat("exitCode should be 0, this indicates new item generated ", exitCode, equalTo(0));
		assertThat("some errors should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}
	
	@Test
	public void tUpdateOkDb() throws Exception {
		List<String> updatedbOptions = new NutchJobOptionBuilder("test_crawl", 3).withUpdateDbParameterBuilder().and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, updatedbOptions);
		csp.call();
		int exitCode = csp.getExitCode();
		assertThat("exitCode should be 0, this indicates new item generated ", exitCode, equalTo(0));
		assertThat("some errors should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}

}
