package com.jianglibo.nutchbuilder.template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;
import com.jianglibo.nutchbuilder.repository.NutchBuilderTemplateRepository;

//@Component
// unused.
public class TemplateHolder implements InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(TemplateHolder.class);
	
	@Autowired
	private NutchBuilderTemplateRepository nbtRepo;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	private Map<String, Set<String>> templates = new HashMap<>();
	
	private static final Set<String> excludeFolders = new HashSet<>();
	static {
		excludeFolders.add("build");
		excludeFolders.add("runtime");
		excludeFolders.add("log");
		excludeFolders.add("test");
		excludeFolders.add(".git");
		excludeFolders.add(".settings");
		excludeFolders.add("docs");
	}

	private static final Set<String> excludeExts = new HashSet<>();
	static {
		excludeExts.add(".JAR");
		excludeExts.add(".CLASS");
		excludeExts.add(".SWF");
	}
	
	private static final Set<String> includeExts = new HashSet<>();
	static {
		includeExts.add(".JAVA");
		includeExts.add(".PROPERTIES");
		includeExts.add(".XML");
	}

	
	protected String getExtWithDotUpcased(Path p) {
		String s = p.getFileName().toString();
		int i = s.lastIndexOf('.');
		if (i == -1) {
			return "";
		}
		return s.substring(i).toUpperCase();
	}
	
	private Predicate<Path> filter = new Predicate<Path>() {
		@Override
		public boolean test(Path t) {
			if (!includeExts.contains(getExtWithDotUpcased(t))) {
				return false;
			}
			Iterator<Path> it = t.iterator();
			while(it.hasNext()) {
				String ps = it.next().toString();
				if (excludeFolders.contains(ps)) {
					return false;
				}
			}
			return true;
		}
	};

	@Override
	public void afterPropertiesSet() throws Exception {
		List<NutchBuilderTemplate> tpls = nbtRepo.findAll();
		for(NutchBuilderTemplate nbt: tpls) {
			templates.put(nbt.getName(), loadOne(nbt));
		}
	}
	
	public void addNewTemplate(String name) throws IOException {
		NutchBuilderTemplate nbt = nbtRepo.findByName(name);
		if (nbt == null) {
			nbt = new NutchBuilderTemplate();
			nbt.setName(name);
			nbt = nbtRepo.save(nbt);
			loadOne(nbt);
		}
	}
	
	private Set<String> loadOne(NutchBuilderTemplate nbt) throws IOException {
		final Set<String> set = new HashSet<>();
		Path nbtpath = applicationConfig.getTemplateRootPath().resolve(nbt.getName()).normalize();
		if (!Files.exists(nbtpath)) {
			log.error("template folder {} doesn't exists.", nbtpath);
			return set;
		}
		Assert.isTrue(nbtpath.startsWith(applicationConfig.getTemplateRootPath()));
		Files.walk(nbtpath).filter(filter).filter(dorf -> !Files.isDirectory(dorf)).forEach(f -> {
			String rp = nbtpath.relativize(f).toString();
			set.add(rp);
		});
		return set;
	}

	public Map<String, Set<String>> getTemplates() {
		return templates;
	}
}
