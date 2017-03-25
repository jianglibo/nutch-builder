package com.jianglibo.nutchbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;

@Component
public class TestUtil implements InitializingBean {
	
	public Properties properties;
	
	public  String SEED_DIR;
	public  String HDFS_USERHOME;
	public  String HADOOP_EXECUTABLE;
	public  String HDFS_USER_TEST_FOLDER;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
//	static {
//		try {
//			InputStream is = ClassLoader.getSystemResourceAsStream("t.properties");
//			properties = new Properties();
//			properties.load(is);
//			is.close();
//			SEED_DIR = String.format("hdfs://%s%s/user/%s/nutch/fhgov/seeds.txt", properties.getProperty("hdfsHost"),properties.getProperty("hdfsPort"), System.getProperty("user.name"));
//			HDFS_USERHOME = String.format("hdfs://%s%s/user/%s", properties.getProperty("hdfsHost"),properties.getProperty("hdfsPort"), System.getProperty("user.name"));
//			HADOOP_EXECUTABLE = properties.getProperty("hadoopExecutable");
//			HDFS_USER_TEST_FOLDER = HDFS_USERHOME + "/fort";
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static Path tProjectRoot = Paths.get("e:/nutchBuilderRoot/buildRoot/a");
	
	

	@Override
	public void afterPropertiesSet() throws Exception {
		SEED_DIR = String.format("hdfs://%s%s/user/%s/nutch/fhgov/seeds.txt", applicationConfig.getHdfsHost(), applicationConfig.getHdfsPort(), System.getProperty("user.name"));
		HDFS_USERHOME = String.format("hdfs://%s%s/user/%s", applicationConfig.getHdfsHost(), applicationConfig.getHdfsPort(), System.getProperty("user.name"));
		HADOOP_EXECUTABLE = applicationConfig.getHadoopExecutable();
		HDFS_USER_TEST_FOLDER = HDFS_USERHOME + "/fort";
	}
	
	
}
