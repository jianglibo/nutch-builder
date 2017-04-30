package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;

import org.junit.Test;

import com.jianglibo.nutchbuilder.crawl.CrawlProcesses.CrawlStepProcess;

public class TestUpdatedbStep extends StepBase {
	
	/**
	 * a TestInjectStep must had been run once. 
	 * @throws Exception
	 */
//	@Before
//	public void b() throws Exception {
//		chir.deleteTable(testHtableName);
//		injectTestSeedDir();
//		runTestGenerate();
//	}
	
	
	@Test
	public void tParse() throws Exception {
		CrawlStepProcess csp = runTestUpdatedb();
		int exitCode = csp.getExitCode();
		// return 1 means no more item generated.
		assertThat("exitCode should be 0, this indicates new item generated ", exitCode, equalTo(0));
		assertThat("some errors should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}

}
