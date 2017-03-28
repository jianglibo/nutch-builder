package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.jianglibo.nutchbuilder.crawl.CrawlProcesses.CrawlStepProcess;

public class TestCrawlLoop extends StepBase {
	
	@Before
	public void b() throws Exception {
		chir.deleteTable(testHtableName);
		injectTestSeedDir();
	}
	
	@Test
	public void loop() throws Exception {
		CrawlStepProcess csp;
		int i = 0;
		while(true) {
			csp = runTestGenerate();
			if (csp.getExitCode() == 1) {
				break;
			}
			csp = runTestFetching();
			assertThat("fetching should always return 0", csp.getExitCode(), equalTo(0));
			csp = runTestParse();
			assertThat("parse should always return 0", csp.getExitCode(), equalTo(0));
			csp = runTestUpdatedb();
			assertThat("updatedb should always return 0", csp.getExitCode(), equalTo(0));
			i++;
		}
		
		assertThat("the loop count should be right.", i, equalTo(10));
	}
}
