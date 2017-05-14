package com.jianglibo.nutchbuilder.startup;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;

@Component
public class CrawlCatInit implements InitializingBean {
	
	private static Logger LOG = LoggerFactory.getLogger(CrawlCatInit.class);
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	@Autowired
	private CrawlCatRepository crawlCatRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		Path p = applicationConfig.getBuildRootPath();
		if (!(Files.exists(p) && Files.isDirectory(p))) {
			return;
		}
		File[] files = p.toFile().listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		
		Stream.of(files).forEach(dir -> {
			try {
				CrawlCat cc = new CrawlCat();
				cc.setName(dir.getName());
				crawlCatRepository.save(cc);
			} catch (Exception e) {
				LOG.info("crawlCat {} already exists. skiping...", dir.getName());
			}
		});
	}
}
