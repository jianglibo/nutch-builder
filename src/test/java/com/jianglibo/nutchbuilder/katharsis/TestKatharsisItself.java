package com.jianglibo.nutchbuilder.katharsis;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.jianglibo.nutchbuilder.KatharsisBase;

import io.katharsis.module.Module;
import io.katharsis.resource.information.ResourceInformation;
import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;

public class TestKatharsisItself extends KatharsisBase {
	
	@Test
	public void t() {
		List<Module> modules = kboot.getModuleRegistry().getModules();
		for(Module m : modules) {
			printme(m.getModuleName());
		}
	}
	
	@Test
	public void tt() {
		ResourceRegistry rg = kboot.getResourceRegistry();
		Collection<RegistryEntry> res =  rg.getResources();
		res.stream().forEach(re -> {
			ResourceInformation rif = re.getResourceInformation();
			String rurl = rg.getResourceUrl(rif);
			printme(rurl);
		});
	}

	@Override
	protected String getResourceName() {
		return null;
	}

}
