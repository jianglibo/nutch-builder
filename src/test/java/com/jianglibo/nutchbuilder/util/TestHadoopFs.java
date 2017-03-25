package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.util.HadoopFs.HadoopFsResult;
import com.jianglibo.nutchbuilder.util.HadoopFs.LsResult;
import com.jianglibo.nutchbuilder.util.HadoopFs.RM_OPTS;

public class TestHadoopFs extends Tbase {
	
	@Autowired
	private HadoopFs hadoopFs;
	
	
	@Before
	public void b() {
		HadoopFsResult hfr = hadoopFs.rm(testUtil.HDFS_USER_TEST_FOLDER, RM_OPTS.IGNORE_NOT_EXIST, RM_OPTS.RECURSIVE);
		assertThat(hfr.getExitCode(), equalTo(0));
		hfr = hadoopFs.mkdir(testUtil.HDFS_USER_TEST_FOLDER, true);
		assertThat(hfr.getExitCode(), equalTo(0));
		
	}
	

	@Test
	public void tls() {
		HadoopFsResult hfr = hadoopFs.put(testUtil.HDFS_USER_TEST_FOLDER, Paths.get("fixturesingit", "lst.txt"));
		hfr = hadoopFs.mkdir(testUtil.HDFS_USER_TEST_FOLDER + "/tint", true);
		hfr = hadoopFs.ls(testUtil.HDFS_USER_TEST_FOLDER);
		assertThat(hfr.getExitCode(), equalTo(0));
		List<LsResult> lsResults = hfr.returnLsResult();
		assertThat(lsResults.size(), equalTo(2));
		LsResult d = lsResults.stream().filter(LsResult::isDirectory).findFirst().get();
		LsResult f = lsResults.stream().filter(lr -> !lr.isDirectory()).findFirst().get();
		assertThat(f.getFilesize(), equalTo(11L));
		
	}
}
