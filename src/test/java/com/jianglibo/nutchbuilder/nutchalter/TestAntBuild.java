package com.jianglibo.nutchbuilder.nutchalter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;

import org.junit.Test;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.nutchalter.AntBuild.Build;

public class TestAntBuild extends Tbase {

	@Test
	public void testBuild() throws Exception {
		Build bd = new AntBuild.Build(StepBase.neighborProjectRoot, applicationConfig.getAntExec(), "runtime");
		int i = bd.call();
		assertThat(i, equalTo(0));
		assertTrue("log file should exists.", Files.exists(bd.getPblog()));
	}
	
}
