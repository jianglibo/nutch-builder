package com.jianglibo.nutchbuilder.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jianglibo.nutchbuilder.TestUtil;
import com.jianglibo.nutchbuilder.util.HadoopFs.HadoopFsResult;

public class TestHadoopFs {
	
	@BeforeClass
	public static void bc() {
		HadoopFs.HADOOP_CMD = "E:\\hadoop-2.7.3\\bin\\hadoop.cmd";
	}

	@Test
	public void tls() {
		HadoopFsResult hfr = HadoopFs.ls(TestUtil.HDFS_USERHOME);
		assertThat(hfr.getExitCode(), equalTo(0));
		hfr.getOutLines().forEach(line -> {
			System.out.println(line);
		});
	}
}
