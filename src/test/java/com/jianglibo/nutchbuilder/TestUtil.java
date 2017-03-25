package com.jianglibo.nutchbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class TestUtil {
	
	public static Properties properties;
	
	public static String SEED_DIR = "hdfs://s62.host.name/user/" + System.getProperty("user.name") + "admin/nutch/fhgov/seeds.txt";
	public static String HDFS_USERHOME = "hdfs://s62.host.name/user/" + System.getProperty("user.name") + "/";
	
	static {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("t.properties");
			properties = new Properties();
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Path tProjectRoot = Paths.get("e:/nutchBuilderRoot/buildRoot/a");
}
