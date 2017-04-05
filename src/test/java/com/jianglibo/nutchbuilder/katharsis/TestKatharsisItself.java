package com.jianglibo.nutchbuilder.katharsis;

import java.util.List;

import org.junit.Test;

import com.jianglibo.nutchbuilder.KatharsisBase;

import io.katharsis.module.Module;

public class TestKatharsisItself extends KatharsisBase {
	
	@Test
	public void t() {
		List<Module> modules = kboot.getModuleRegistry().getModules();
		for(Module m : modules) {
			printme(m.getModuleName());
		}
	}

	@Override
	protected String getResourceName() {
		return null;
	}

}
