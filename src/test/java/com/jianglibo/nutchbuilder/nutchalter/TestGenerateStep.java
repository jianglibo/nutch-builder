package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

import com.jianglibo.nutchbuilder.crawl.NutchJobOptionBuilder;
import com.jianglibo.nutchbuilder.nutchalter.CrawlProcesses.CrawlStepProcess;

public class TestGenerateStep extends StepBase {
	
	@Test
	public void tGenerate() throws Exception {
		List<String> generateOptions = new NutchJobOptionBuilder("test_crawl", 3).withGenerateParameterBuilder(batchId).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, generateOptions);
		csp.call();
		int exitCode = csp.getExitCode();
		// return 1 means no more item generated.
		assertThat("exitCode should be 0, this indicates new item generated ", exitCode, equalTo(0));
		assertThat("some errors should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
		
		generateOptions = new NutchJobOptionBuilder("test_crawl", 3).withGenerateParameterBuilder(batchId).and().buildStringList();
		csp = CrawlProcesses.newStep(neighborProjectRoot, generateOptions);
		csp.call();
		exitCode = csp.getExitCode();
		// return 1 means no more item generated.
		assertThat("exitCode should be 1, means no new item generated ", exitCode, equalTo(1));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}
}
