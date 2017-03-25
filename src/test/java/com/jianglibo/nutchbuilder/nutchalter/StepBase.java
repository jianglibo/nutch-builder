package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.BeforeClass;

import com.jianglibo.nutchbuilder.Tbase;

public class StepBase extends Tbase {

	protected static Path neighborProjectRoot = Paths.get("..", "nutch");
	
	protected String batchId = "193828973-26276356376";
	
	@BeforeClass
	public static void b() throws IOException {
		copyNeighborScript();
	}

	private static void copyNeighborScript() throws IOException {
		Files.copy(neighborProjectRoot.resolve("src/bin/crawl.ps1"), neighborProjectRoot.resolve("runtime/deploy/bin/crawl.ps1"), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(neighborProjectRoot.resolve("src/bin/nutch.ps1"), neighborProjectRoot.resolve("runtime/deploy/bin/nutch.ps1"), StandardCopyOption.REPLACE_EXISTING);
	}
	
	
}
