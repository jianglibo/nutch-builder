package com.jianglibo.nutchbuilder.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HadoopFs {

	private static Logger log = LoggerFactory.getLogger(HadoopFs.class);
	
	public static String HADOOP_CMD = "hadoop";

	/**
	 * 
	 * @param hdfsURI
	 * @param localPaths
	 * @return
	 */
	public static HadoopFsResult put(String hdfsURI, Path...localPaths) {
		HfsCmdBuilder hc = new HfsCmdBuilder("put");
		for(Path lp : localPaths) {
			hc.addItems(lp.normalize().toAbsolutePath().toString());
		}
		hc.addItems(hdfsURI);
		return execute(hc.buildArray());
	}
	
	public static enum RM_OPTS {
		IGNORE_NOT_EXIST("-f"),RECURSIVE("-r"),SKIP_TRASH("-skipTrash");
		private String value;
		private RM_OPTS(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public static HadoopFsResult rm(String[] hdfsURIs, RM_OPTS...opts) {
		HfsCmdBuilder hc = new HfsCmdBuilder("rm");
		for(RM_OPTS opt: opts) {
			hc.addItems(opt.getValue());
		}
		for(String uri : hdfsURIs) {
			hc.addItems(uri);
		}
		return execute(hc.buildArray());
	}
	
	public static HadoopFsResult rm(String hdfsURIs, RM_OPTS...opts) {
		return rm(new String[]{hdfsURIs}, opts);
	}
	
	
	public static enum LS_OPTS {
		DIRECOTRY_AS_PLANFIL("-d"),HUMAN_READABLE("-h"),RECURSIVE("-r");
		private String value;
		private LS_OPTS(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * Usage: hadoop fs -ls [-d] [-h] [-R] <args>
	 * @param hdfsURI
	 * @param opts
	 * @return
	 */
	public static HadoopFsResult ls(String hdfsURI,LS_OPTS...opts) {
		HfsCmdBuilder hc = new HfsCmdBuilder("ls");
		for(LS_OPTS opt: opts) {
			hc.addItems(opt.getValue());
		}
		hc.addItems(hdfsURI);
		return execute(hc.buildArray());
	}

	private static HadoopFsResult execute(String...cmds) {
		BufferedReader br = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(cmds);
			pb.redirectErrorStream(true);
			log.info("starting invoking cmd: {}", String.join(" ", cmds));

			Process p = pb.start();

			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			List<String> lines = new ArrayList<>();
			String line = null;
			while (null != (line = br.readLine())) {
				lines.add(line);
			}
			p.waitFor();
			return new HadoopFsResult(p.exitValue(), lines);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static class HfsCmdBuilder {
		private List<String> cmdItems = new ArrayList<>();
		
		public HfsCmdBuilder(String cmd) {
			cmdItems.add(HADOOP_CMD);
			cmdItems.add("fs");
			if (cmd.startsWith("-")) {
				cmdItems.add(cmd);
			} else {
				cmdItems.add("-" + cmd);
			}
		}
		
		public void addItems(String...items) {
			for(String it : items) {
				cmdItems.add(it);
			}
		}
		
		public String[] buildArray() {
			return cmdItems.toArray(new String[]{});
		}
		
		public List<String> buildList() {
			return cmdItems;
		}

	}
	
	public static class HadoopFsResult {
		private int exitCode;
		private List<String> outLines;

		public HadoopFsResult(int exitCode, List<String> outLines) {
			super();
			this.exitCode = exitCode;
			this.outLines = outLines;
		}

		public int getExitCode() {
			return exitCode;
		}

		public void setExitCode(int exitCode) {
			this.exitCode = exitCode;
		}

		public List<String> getOutLines() {
			return outLines;
		}

		public void setOutLines(List<String> outLines) {
			this.outLines = outLines;
		}

	}
}
