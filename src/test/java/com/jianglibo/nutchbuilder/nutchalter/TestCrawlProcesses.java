package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jianglibo.nutchbuilder.TestUtil;
import com.jianglibo.nutchbuilder.crawl.NutchJobOptionBuilder;
import com.jianglibo.nutchbuilder.nutchalter.CrawlProcesses.CrawlStepProcess;

public class TestCrawlProcesses {
	
	private Path neighborProjectRoot = Paths.get("..", "nutch");
	
	private String batchId = "193828973-26276356376";
	
	@Before
	public void b() throws IOException {
		copyNeighborScript();
	}

	private void copyNeighborScript() throws IOException {
		Files.copy(neighborProjectRoot.resolve("src/bin/crawl.ps1"), neighborProjectRoot.resolve("runtime/deploy/bin/crawl.ps1"), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(neighborProjectRoot.resolve("src/bin/nutch.ps1"), neighborProjectRoot.resolve("runtime/deploy/bin/nutch.ps1"), StandardCopyOption.REPLACE_EXISTING);
	}
	
	@Test
	public void t() throws Exception {
//		CrawlStepProcess cp = CrawlProcesses.newCrawlBuilder(neighborProjectRoot, "test_crawl", 1).build();
//		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		List<String> injectOptions = new NutchJobOptionBuilder("test_crawl", 1).withInjectJobParameterBuilder().seedDir(TestUtil.SEED_DIR).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, injectOptions);
		int exitCode = csp.call();
		assertThat("exitCode should be 0", exitCode, equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}

}
