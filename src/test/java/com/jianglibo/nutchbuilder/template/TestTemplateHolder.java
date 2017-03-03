package com.jianglibo.nutchbuilder.template;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.Tbase;

public class TestTemplateHolder extends Tbase {

	private static final String EXAMPLE_TPL = "apache-nutch-2.3.1";
	
	@Autowired
	private TemplateHolder templateHolder;
	
	@Test
	public void tPathIterator() {
		Path path = Paths.get("a", "b", "c");
		final Set<String> splited = new HashSet<>();
		
		path.forEach(p -> {
			splited.add(p.toString());
		});
		assertThat(splited, containsInAnyOrder("a","b","c"));
	}
	
	@Test
	public void tExt() {
		TemplateHolder th = new TemplateHolder();
		assertThat(th.getExtWithDotUpcased(Paths.get("abc")), equalTo(""));
		assertThat(th.getExtWithDotUpcased(Paths.get("abc.1")), equalTo(".1"));
		assertThat(th.getExtWithDotUpcased(Paths.get("abc.class")), equalTo(".CLASS"));
	}
	
	@Test
	public void tLoadTemplate() throws IOException {
		templateHolder.addNewTemplate(EXAMPLE_TPL);
		Set<String> fns = templateHolder.getTemplates().get(EXAMPLE_TPL);
		for(String fn : fns) {
			System.out.println(fn);
		}
		
		System.out.println(fns.size());
	}
}
