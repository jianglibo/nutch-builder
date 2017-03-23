package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.jianglibo.nutchbuilder.util.DirectoryUtil;

public class CrawlProcesses {

	private static Pattern unjarPtn = Pattern.compile(".*fs\\.FileUtil: Failed.*\\[([^\\]]+)\\].*");
	
	private static Logger log = LoggerFactory.getLogger(CrawlProcesses.class);
	
	private Path unjarRoot;
	
	@Value("${myapp.unjarRoot}")
	public void setUnjarRoot(String unjarRootStr) {
		this.unjarRoot = Paths.get(unjarRootStr);
		if (!Files.exists(this.unjarRoot)) {
			this.unjarRoot = Paths.get(System.getProperty("java.io.tmpdir"));
		}
	}
	
	protected static CrawlStepProcess newStep(Path projectRoot, List<String> optlist) {
		List<String> cmdList = new ArrayList<>();
		cmdList.add("powershell");
		cmdList.add("-F");
		cmdList.add(projectRoot.resolve("src/bin/nutch.ps1").toAbsolutePath().normalize().toString());
		cmdList.addAll(optlist);
		return new CrawlStepProcess(projectRoot.toAbsolutePath().normalize(), cmdList.toArray(new String[]{}));
	}


	
	public static class CrawlStepProcess implements Callable<Integer> {
		
		private final Path projectRoot;
		
		private final Path pblog;
		
		private final String[] cmds;
		
		private Path unjarPath;
		
		public CrawlStepProcess(Path projectRoot, String...cmds) {
			this.projectRoot = projectRoot;
			this.pblog = projectRoot.resolve("pb.log");
			this.cmds = cmds;
		}

		@Override
		public Integer call() throws Exception {
			 ProcessBuilder pb =
					   new ProcessBuilder(cmds);
			 if (Files.exists(pblog)) {
				 Files.delete(pblog);
			 }
			 pb.directory(projectRoot.toFile());
			 pb.redirectErrorStream(true);
			 pb.redirectOutput(Redirect.appendTo(pblog.toFile()));
			 log.info("starting invoking cmd: {}", String.join(" ", cmds));
			 Process p = pb.start();
			 p.waitFor();
			 deleteUnjarIfNotDeleted();
			 return p.exitValue();
		}
		
		private void deleteUnjarIfNotDeleted() {
			try {
				Optional<String> mayBeMatched = Files.lines(pblog).filter(line -> unjarPtn.matcher(line).matches()).findFirst();
				if (mayBeMatched.isPresent()) {
					Matcher m = unjarPtn.matcher(mayBeMatched.get());
					if (m.matches()) {
						Path jarp = Paths.get(m.group(1));
						unjarPath = DirectoryUtil.findMiddlePathStartsWith(jarp, "hadoop-unjar");
						DirectoryUtil.deleteRecursiveIgnoreFailed(unjarPath);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public Path getProjectRoot() {
			return projectRoot;
		}

		public Path getPblog() {
			return pblog;
		}

		public Path getUnjarPath() {
			return unjarPath;
		}
	}
	
	
	// --- whole crawl ---
	
	private static CrawlProcessBuilder newCrawlBuilder(Path projectRoot, String crawlId, int limit) {
		 return new CrawlProcessBuilder(projectRoot, crawlId, limit);
	}

	
	private static class CrawlProcessBuilder {
		private final String crawlId;
		private final int limit;
		private String seedDir;
		private int numSlaves = 1;
		private boolean forceFetching;
		private int addDays = 0;
		
		private final Path projectRoot;
		
		public CrawlProcessBuilder(Path projectRoot, String crawlId, int limit) {
			super();
			this.projectRoot = projectRoot;
			this.crawlId = crawlId;
			this.limit = limit;
		}

		public CrawlProcessBuilder seedDir(String seedDir) {
			this.seedDir = seedDir;
			return this;
		}
		public CrawlProcessBuilder numSlaves(int numSlaves) {
			this.numSlaves = numSlaves;
			return this;
		}
		public CrawlProcessBuilder forceFetching(boolean forceFetching) {
			this.forceFetching = forceFetching;
			return this;
		}
		public CrawlProcessBuilder addDays(int addDays) {
			this.addDays = addDays;
			return this;
		}
		public CrawlStepProcess build() {
			List<String> cmdList = new ArrayList<>();
			cmdList.add("powershell");
			cmdList.add("-F");
			cmdList.add(projectRoot.resolve("src/bin/crawl.ps1").toAbsolutePath().normalize().toString());
			cmdList.add("-CRAWL_ID");
			cmdList.add(crawlId);
			cmdList.add("-LIMIT");
			cmdList.add(String.valueOf(limit));
			if (addDays > 0) {
				cmdList.add("-ADD_DAYS");
				cmdList.add(String.valueOf(addDays));
			}
			if (seedDir == null || seedDir.trim().isEmpty()) {
				cmdList.add("-SKIP_INJECT");
			} else {
				cmdList.add("-SEEDDIR");
				cmdList.add(seedDir);
			}
			if (forceFetching) {
				cmdList.add("-FORCE_FETCHING");
			}
			cmdList.add("-NUM_SLAVES");
			cmdList.add(String.valueOf(numSlaves));
			CrawlStepProcess crawl = new CrawlStepProcess(projectRoot, cmdList.toArray(new String[]{}));
			return crawl;
		}
	}
}
