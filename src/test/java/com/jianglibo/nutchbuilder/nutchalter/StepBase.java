package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.util.HadoopFs;

public class StepBase extends Tbase {

	public static Path neighborProjectRoot = Paths.get("..", "nutch").toAbsolutePath().normalize();
	
	protected String batchId = "193828973-26276356376";
	
	public static String testCrawlId = "test_crawl";
	
	public static String testHtableName = testCrawlId + "_webpage";
	
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
	
	
}
