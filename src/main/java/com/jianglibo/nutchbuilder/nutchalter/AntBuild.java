package com.jianglibo.nutchbuilder.nutchalter;

import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class AntBuild {

	
	public static class Build implements Callable<Integer> {
		
		private final Path projectRoot;
		
		private final Path pblog;
		
		private final String[] cmds;
		
		public Build(Path projectRoot, String...cmds) {
			this.projectRoot = projectRoot;
			this.pblog = projectRoot.resolve("pb.log");
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
			 if (Files.exists(pblog)) {
				 Files.delete(pblog);
			 }
			 pb.directory(projectRoot.toFile());
			 pb.redirectErrorStream(true);
			 pb.redirectOutput(Redirect.appendTo(pblog.toFile()));
			 Process p = pb.start();
			 p.waitFor();
			 return p.exitValue();
		}

		public Path getProjectRoot() {
			return projectRoot;
		}

		public Path getPblog() {
			return pblog;
		}
	}
}
