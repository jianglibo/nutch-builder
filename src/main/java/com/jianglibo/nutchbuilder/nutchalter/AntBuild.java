package com.jianglibo.nutchbuilder.nutchalter;

import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntBuild {
	
	private static Logger log = LoggerFactory.getLogger(AntBuild.class);
	
	public static class Build implements Callable<Integer> {
		
		private final Path projectRoot;
		
		private final Path antout;
		
		private final String[] cmds;
		
		public Build(Path projectRoot, String...cmds) {
//			if (System.getProperty("os.name").toUpperCase().matches(".*WIN.*")) {
//				cmds = Stream.of(Stream.of("C:\\Windows\\system32\\cmd.exe", "/C"), Stream.of(cmds)).flatMap(c -> c).toArray(size -> new String[size]);
//			}
			projectRoot = projectRoot.toAbsolutePath().normalize();
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
			this.projectRoot = projectRoot;
			this.antout = projectRoot.resolve("logs").resolve("ant-" + now + ".log");
			this.cmds = cmds;
		}

		@Override
		public Integer call() throws Exception {
			 ProcessBuilder pb =
					   new ProcessBuilder(cmds);
//			 Map<String, String> env = pb.environment();
//			 env.put("VAR1", "myValue");
//			 env.remove("OTHERVAR");
//			 env.put("VAR2", env.get("VAR1") + "suffix");
			 pb.directory(projectRoot.toFile());
			 pb.redirectErrorStream(true);
			 if (!Files.exists(antout.getParent())) {
				 Files.createDirectories(antout.getParent());
			 }
			 pb.redirectOutput(Redirect.to(antout.toFile()));
			 log.info("start execute: {}, log to: {}", String.join(" ", cmds), antout.toString());
			 Process p = pb.start();
			 p.waitFor();
			 return p.exitValue();
		}

		public Path getProjectRoot() {
			return projectRoot;
		}

		public Path getPblog() {
			return antout;
		}
	}
}
