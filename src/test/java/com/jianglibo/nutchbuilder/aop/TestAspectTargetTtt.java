package com.jianglibo.nutchbuilder.aop;

import org.junit.Test;

import com.jianglibo.nutchbuilder.Tbase;

public class TestAspectTargetTtt extends Tbase {
	
	@Test
	public void t() {
		AspectTargetTtt att = context.getBean(AspectTargetTtt.class);
		att.beAdvised("abc");
	}

}
