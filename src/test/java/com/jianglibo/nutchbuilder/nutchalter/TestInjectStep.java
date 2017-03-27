package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.crawl.CrawlProcesses;
import com.jianglibo.nutchbuilder.crawl.NutchJobOptionBuilder;
import com.jianglibo.nutchbuilder.crawl.CrawlProcesses.CrawlStepProcess;
import com.jianglibo.nutchbuilder.hbaserest.CommonHbaseInformationRetriver;
import com.jianglibo.nutchbuilder.hbaserest.HbaseTableSchema;
import com.jianglibo.nutchbuilder.util.HadoopFs.RM_OPTS;

public class TestInjectStep extends StepBase {
	
	@Autowired
	private CommonHbaseInformationRetriver chir;
	
	
	@Before
	public void b() {
		hadoopFs.rm(testUtil.SEED_DIR, RM_OPTS.RECURSIVE, RM_OPTS.IGNORE_NOT_EXIST, RM_OPTS.SKIP_TRASH);
		chir.deleteTable(testHtableName);
	}
	
	@Test
	public void tInject() throws Exception {
//		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		
		HbaseTableSchema hts = chir.getTableSchema(testHtableName);
		
		assertNull(testHtableName + "table should not exists before inject", hts);
		
		Path[] sfs = Files.list(Paths.get("fixturesingit", "test_seeddir")).filter(Files::isRegularFile).toArray(size -> new Path[size]);
		hadoopFs.mkdir(testUtil.SEED_DIR, true);
		hadoopFs.put(testUtil.SEED_DIR, sfs);
		
		// alter regex-urlfilter.txt
		new RegexUrlFilterConfFile().withDefaultSkips().addAccept("+^http://www.jianglibo.com/.*").doAlter(neighborProjectRoot);
		
		// alter hbase-site.xml
		
		new HbaseSite().fromHbaseHomeEnv(neighborProjectRoot.resolve("conf").resolve("hbase-site.xml"));
		
		// build project.
		 new AntBuild.Build(neighborProjectRoot, applicationConfig.getAntExec(),"clean").call();
		 new AntBuild.Build(neighborProjectRoot, applicationConfig.getAntExec(),"runtime").call();
		
		List<String> injectOptions = new NutchJobOptionBuilder(testCrawlId, 1).withInjectJobParameterBuilder().seedDir(testUtil.SEED_DIR).and().buildStringList();
		CrawlStepProcess csp = CrawlProcesses.newStep(neighborProjectRoot, injectOptions);
		csp.call();
		int exitCode = csp.getExitCode();
		assertThat("exitCode should be 0", exitCode, equalTo(0));
		assertThat("no error should be existed", csp.getErrorLines().size(), equalTo(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
		
		hts = chir.getTableSchema(testHtableName);
		
		assertNotNull(testHtableName + "table should exists.", hts);

		injectOptions = new NutchJobOptionBuilder(testCrawlId, 1).withInjectJobParameterBuilder().seedDir("/notexistfolder").and().buildStringList();
		csp = CrawlProcesses.newStep(neighborProjectRoot, injectOptions);
		csp.call();
		exitCode = csp.getExitCode();
		assertThat("exitCode should be -1", exitCode, equalTo(-1));
		assertThat("some errors should be existed", csp.getErrorLines().size(), greaterThan(0));
		assertFalse(Files.exists(csp.getUnjarPath()));
	}
}
