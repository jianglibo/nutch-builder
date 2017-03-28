package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.crawl.CrawlProcesses;
import com.jianglibo.nutchbuilder.crawl.NutchJobOptionBuilder;
import com.jianglibo.nutchbuilder.crawl.CrawlProcesses.CrawlStepProcess;
import com.jianglibo.nutchbuilder.hbaserest.CommonHbaseInformationRetriver;
import com.jianglibo.nutchbuilder.util.HadoopFs;
import com.jianglibo.nutchbuilder.util.HadoopFs.RM_OPTS;

public class StepBase extends Tbase {

	public static Path neighborProjectRoot = Paths.get("..", "nutch").toAbsolutePath().normalize();
	
	protected String batchId = "193828973-26276356376";
	
	public static String testCrawlId = "test_crawl";
	
	public static String testHtableName = testCrawlId + "_webpage";
	
	@Autowired
	protected CommonHbaseInformationRetriver chir;
	
	@Autowired
	protected HadoopFs hadoopFs;
	
	@BeforeClass
	public static void bb() throws IOException {
		copyNeighborScript();
	}

	private static void copyNeighborScript() throws IOException {
		try {
			Files.copy(neighborProjectRoot.resolve("src/bin/crawl.ps1"), neighborProjectRoot.resolve("runtime/deploy/bin/crawl.ps1"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(neighborProjectRoot.resolve("src/bin/nutch.ps1"), neighborProjectRoot.resolve("runtime/deploy/bin/nutch.ps1"), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteSeedDir() {
		hadoopFs.rm(testUtil.SEED_DIR, RM_OPTS.RECURSIVE, RM_OPTS.IGNORE_NOT_EXIST, RM_OPTS.SKIP_TRASH);
		chir.deleteTable(testHtableName);
	}
	
	public CrawlStepProcess injectTestSeedDir() throws Exception {
		List<String> injectOptions = new NutchJobOptionBuilder(testCrawlId, 1).withInjectJobParameterBuilder().seedDir(testUtil.SEED_DIR).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, injectOptions);
		csp.call();
		return csp;
	}
	
	public CrawlStepProcess runTestGenerate() throws Exception {
		List<String> generateOptions = new NutchJobOptionBuilder(testCrawlId, 3).withGenerateParameterBuilder(batchId).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, generateOptions);
		csp.call();
		return csp;
	}
	
	public CrawlStepProcess runTestFetching() throws Exception {
		List<String> fetchingOptions = new NutchJobOptionBuilder(testCrawlId, 3).withFetchParameterBuilder(batchId).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, fetchingOptions);
		csp.call();
		return csp;
	}

	public CrawlStepProcess runTestParse() throws Exception {
		List<String> fetchingOptions = new NutchJobOptionBuilder(testCrawlId, 3).withParseParameterBuilder(batchId).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, fetchingOptions);
		csp.call();
		return csp;
	}
	
	public CrawlStepProcess runTestUpdatedb() throws Exception {
		List<String> fetchingOptions = new NutchJobOptionBuilder(testCrawlId, 3).withUpdateDbParameterBuilder(batchId).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, fetchingOptions);
		csp.call();
		return csp;
	}
	
}
