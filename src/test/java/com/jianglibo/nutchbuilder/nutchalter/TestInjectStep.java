package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jianglibo.nutchbuilder.crawl.CrawlProcesses;
import com.jianglibo.nutchbuilder.crawl.NutchJobOptionBuilder;
import com.jianglibo.nutchbuilder.crawl.CrawlProcesses.CrawlStepProcess;
import com.jianglibo.nutchbuilder.hbaserest.HbaseTableSchema;

public class TestInjectStep extends StepBase {
	
	@Before
	public void b() throws IOException {
		copyNeighborScript();
		deleteSeedDir();
	}
	
	@Test
	public void tInject() throws Exception {
//		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		
		HbaseTableSchema hts = chir.getTableSchema(testHtableName);
		
		assertNull(testHtableName + "table should not exists before inject", hts);
		
		Path[] sfs = Files.list(Paths.get("fixturesingit", "test_seeddir")).filter(Files::isRegularFile).toArray(size -> new Path[size]);
		hadoopFs.mkdir(testUtil.SEED_DIR, true);
		hadoopFs.put(testUtil.SEED_DIR, sfs);
		
		// alter nutch-site.xml
		Path nutchSitePath = neighborProjectRoot.resolve("conf").resolve("nutch-site.xml");
		new NutchSite()
		.withAdaptiveScheduleClass()
		.withAgentName("fhgov")
		.withDefaultPlugins()
		.withFetchInterval(900)
		.withFetchThreads(10)
		.withHbase()
		.withPairs("db.fetch.schedule.adaptive.inc_rate=0.4",
				"db.fetch.schedule.adaptive.dec_rate=0.2",
				"db.fetch.schedule.adaptive.min_interval=60",
				"db.fetch.schedule.adaptive.max_interval=31536000",
				"db.fetch.schedule.adaptive.sync_delta=true",
				"db.fetch.schedule.adaptive.sync_delta_rate=0.3",
				"db.update.additions.allowed=true").persist(nutchSitePath, nutchSitePath);
		
		// alter regex-urlfilter.txt
		new RegexUrlFilterConfFile().withDefaultSkips().addAccept("+^http://www.jianglibo.com/.*").addAccept("^http://jianglibo.com/.*").doAlter(neighborProjectRoot);
		
		// alter hbase-site.xml
		new HbaseSite().fromHbaseHomeEnv(neighborProjectRoot.resolve("conf").resolve("hbase-site.xml"));
		
		// build project.
		 new AntBuild.Build(neighborProjectRoot, applicationConfig.getAntExec(),"clean").call();
		 new AntBuild.Build(neighborProjectRoot, applicationConfig.getAntExec(),"runtime").call();
		
		CrawlStepProcess csp = injectTestSeedDir();
		int exitCode = csp.getExitCode();
		assertThat("exitCode should be 0", exitCode, equalTo(0));
		assertThat("no error should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
		
		hts = chir.getTableSchema(testHtableName);
		
		assertNotNull(testHtableName + " table should exists.", hts);

		List<String> injectOptions = new NutchJobOptionBuilder(testCrawlId, 1).withInjectJobParameterBuilder().seedDir("/notexistfolder").and().buildStringList();
		csp = CrawlProcesses.newStep(neighborProjectRoot, injectOptions);
		csp.call();
		exitCode = csp.getExitCode();
		assertThat("exitCode should be -1", exitCode, equalTo(-1));
		assertThat("some errors should be existed", csp.getErrorLines().size(), greaterThan(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}
}
