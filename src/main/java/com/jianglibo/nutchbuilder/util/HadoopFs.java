package com.jianglibo.nutchbuilder.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;

@Component
public class HadoopFs implements InitializingBean {

	private static Logger log = LoggerFactory.getLogger(HadoopFs.class);
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	public String HADOOP_CMD = "hadoop";

	/**
	 * 
	 * @param hdfsTarget
	 * @param localPaths
	 * @return
	 */
	public HadoopFsResult put(String hdfsTarget,Path localPath, Path...morePaths) {
		HfsCmdBuilder hc = new HfsCmdBuilder("put");
		hc.addItems(localPath.normalize().toAbsolutePath().toString());
		for(Path lp : morePaths) {
			hc.addItems(lp.normalize().toAbsolutePath().toString());
		}
		hc.addItems(hdfsTarget);
		return execute(hc.buildArray());
	}
	
	public HadoopFsResult cat(String...hdfsURI) {
		HfsCmdBuilder hc = new HfsCmdBuilder("cat");
		for(String uri : hdfsURI) {
			hc.addItems(uri);
		}
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
	
	public HadoopFsResult mkdir(String[] hdfsURIs, boolean createParents) {
		HfsCmdBuilder hc = new HfsCmdBuilder("mkdir");
		if (createParents) {
			hc.addItems("-p");
		}
		for(String uri : hdfsURIs) {
			hc.addItems(uri);
		}
		return execute(hc.buildArray());
	}
	/**
	 * 
	 * @param hdfsURI one url or comma separated urls.
	 * @param createParents
	 * @return
	 */
	public HadoopFsResult mkdir(String hdfsURI, boolean createParents) {
		String[] ddd = hdfsURI.split(",");
		return mkdir(ddd, createParents);
	}
	
	public HadoopFsResult rm(String[] hdfsURIs, RM_OPTS...opts) {
		HfsCmdBuilder hc = new HfsCmdBuilder("rm");
		for(RM_OPTS opt: opts) {
			hc.addItems(opt.getValue());
		}
		for(String uri : hdfsURIs) {
			hc.addItems(uri);
		}
		return execute(hc.buildArray());
	}
	
	public HadoopFsResult rm(String hdfsURIs, RM_OPTS...opts) {
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
	public HadoopFsResult ls(String hdfsURI,LS_OPTS...opts) {
		HfsCmdBuilder hc = new HfsCmdBuilder("ls");
		for(LS_OPTS opt: opts) {
			hc.addItems(opt.getValue());
		}
		hc.addItems(hdfsURI);
		return execute(hc.buildArray());
	}

	private HadoopFsResult execute(String...cmds) {
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
	
	private class HfsCmdBuilder {
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
		
		public List<LsResult> returnLsResult() {
			return outLines.stream().map(LsResult::new).filter(LsResult::isEligible).collect(Collectors.toList());
		}
	}
	
	public static class LsResult {
		private String permission;
		private int numOfReplicas;
		private String userId;
		private String groupId;
		private long filesize;
		private String modificationDate;
		private String modificationTime;
		private String filename;
		private boolean directory;
		private boolean eligible; 
		
		public LsResult(String line) {
			String[] ss = line.split("\\s+");
			if (ss.length == 8) {
				permission = ss[0];
				if ("-".equals(ss[1])) {
					numOfReplicas = -1;
					directory = true;
				} else {
					numOfReplicas = Integer.valueOf(ss[1]);
					directory = false;
				}
				
				userId = ss[2];
				groupId = ss[3];
				filesize = Long.valueOf(ss[4]);
				modificationDate = ss[5];
				modificationTime = ss[6];
				filename = ss[7];
				eligible = true;
			} else {
				eligible = false;
			}
		}
		
		public boolean isDirectory() {
			return directory;
		}

		public boolean isEligible() {
			return eligible;
		}

		public String getPermission() {
			return permission;
		}
		
		public int getNumOfReplicas() {
			return numOfReplicas;
		}
		public String getUserId() {
			return userId;
		}
		public String getGroupId() {
			return groupId;
		}
		public String getModificationDate() {
			return modificationDate;
		}
		public String getModificationTime() {
			return modificationTime;
		}
		public String getFilename() {
			return filename;
		}

		public long getFilesize() {
			return filesize;
		}
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (applicationConfig.getHadoopExecutable() == null || applicationConfig.getHadoopExecutable().trim().isEmpty()) {
			HADOOP_CMD = "hadoop";
		} else {
			HADOOP_CMD = applicationConfig.getHadoopExecutable();			
		}
	}
}
