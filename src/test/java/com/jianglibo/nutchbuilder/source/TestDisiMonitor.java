package com.jianglibo.nutchbuilder.source;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.Tbase;

public class TestDisiMonitor extends Tbase {
	
	@Autowired
	private DiskMonitor dm;
	
	@Test
	public void t() throws IOException, InterruptedException {
//		dm.startRecursiveWatcher();
		Thread.sleep(1000* 60 * 10);
	}

}
