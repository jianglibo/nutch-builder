package com.jianglibo.nutchbuilder.startup;

import java.io.File;
import java.io.FileFilter;
import java.util.stream.Stream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.domain.CrawlCat;
import com.jianglibo.nutchbuilder.repository.CrawlCatRepository;

@Component
public class CrawlCatInit implements InitializingBean {
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	@Autowired
	private CrawlCatRepository crawlCatRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		File[] files = applicationConfig.getBuildRootPath().toFile().listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		
		Stream.of(files).forEach(dir -> {
			CrawlCat cc = new CrawlCat();
			cc.setName(dir.getName());
			crawlCatRepository.save(cc);
		});
	}
}
