package com.jianglibo.nutchbuilder.nutchalter;

import java.util.Properties;

public class HbaseSite {

	private final Properties properties = new Properties();
	
	public HbaseSite withRootDir(String rootDir) {
		properties.put("hbase.rootdir", rootDir);
		return this;
	}
	
	public HbaseSite withZkQuorum(String zkQuorum) {
		properties.put("hbase.zookeeper.quorum", zkQuorum);
		return this;
	}

	public Properties getProperties() {
		return properties;
	}
	
	
}
