package com.jianglibo.nutchbuilder.crawl;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestNutchJobOptionBuilder {
	
	@Test
	public void tInject() {
		NutchJobOptionBuilder njob = new NutchJobOptionBuilder("abc", 1);
		String s = njob.withInjectJobParameterBuilder().seedDir("ssss").and().buildWholeString();
		assertThat(s, startsWith("ssss "));
		System.out.println(s);
	}
	
	
	@Test
	public void tGenerate() {
		NutchJobOptionBuilder njob = new NutchJobOptionBuilder("abc", 1);
		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		String s = njob.withGenerateParameterBuilder(batchId).addDays(1).and().buildWholeString();
		System.out.println(s);
	}
	
	@Test
	public void tFetch() {
		NutchJobOptionBuilder njob = new NutchJobOptionBuilder("abc", 1);
		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		String s = njob.withFetchParameterBuilder(batchId).threads(30).timeLimitFetch(300).and().buildWholeString();
		System.out.println(s);
	}

	@Test
	public void tParse() {
		NutchJobOptionBuilder njob = new NutchJobOptionBuilder("abc", 1);
		String batchId = NutchJobOptionBuilder.getRandomBatchId();
		String s = njob.withParseParameterBuilder(batchId).and().buildWholeString();
		System.out.println(s);
	}
}
