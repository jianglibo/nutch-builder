package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

import com.jianglibo.nutchbuilder.TestUtil;
import com.jianglibo.nutchbuilder.crawl.NutchJobOptionBuilder;
import com.jianglibo.nutchbuilder.nutchalter.CrawlProcesses.CrawlStepProcess;

public class TestInjectStep extends StepBase {
	
	
	@Test
	public void tInjectOk() throws Exception {
//		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		List<String> injectOptions = new NutchJobOptionBuilder("test_crawl", 1).withInjectJobParameterBuilder().seedDir(TestUtil.SEED_DIR).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, injectOptions);
		csp.call();
		int exitCode = csp.getExitCode();
		assertThat("exitCode should be 0", exitCode, equalTo(0));
		assertThat("no error should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}
	
	@Test
	public void tInjectFail() throws Exception {
//		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		List<String> injectOptions = new NutchJobOptionBuilder("test_crawl", 1).withInjectJobParameterBuilder().seedDir("/notexistfolder").and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, injectOptions);
		csp.call();
		int exitCode = csp.getExitCode();
		assertThat("exitCode should be -1", exitCode, equalTo(-1));
		assertThat("some errors should be existed", csp.getErrorLines().size(), greaterThan(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}
	

}
