package com.jianglibo.nutchbuilder.crawl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NutchJobOptionBuilder {
	
		PairOptions pairOptions = new PairOptions();
		JavaDoptions javaDoptions = new JavaDoptions();
		OrphanOptions orphanOptions = new OrphanOptions();
		
		private final int numSlaves;
		
		public NutchJobOptionBuilder(String crawlId, int numSlaves) {
			this.numSlaves = numSlaves;
			addParameter("-crawlId", crawlId);
		}
		
		public InjectJobOptionBuilder withInjectJobParameterBuilder() {
			return new InjectJobOptionBuilder();
		}
		
		public GenerateJobOptionBuilder withGenerateParameterBuilder(String batchId) {
			return new GenerateJobOptionBuilder(batchId);
		}
		
		public FetchJobOptionBuilder withFetchParameterBuilder(String batchId) {
			return new FetchJobOptionBuilder(batchId);
		}
		
		public ParseJobOptionBuilder withParseParameterBuilder(String batchId) {
			return new ParseJobOptionBuilder(batchId);
		}
		
		protected void addCommand(String command) {
			addParameter("-COMMAND", command);
		}
		
		
		public UpdateDbJobOptionBuilder withUpdateDbParameterBuilder() {
			return new UpdateDbJobOptionBuilder();
		}
		
		protected void addJavaDOption(String value) {
			javaDoptions.addJavaDOption(value);
		}
		
		protected void addJavaDOption(String key, Object value) {
			javaDoptions.addJavaDOption(key, String.valueOf(value));
		}
		
		protected void addOrphanOtion(int position, String value) {
			orphanOptions.add(position, value);
		}

		protected void addParameter(String key, Object jp) {
			pairOptions.addPair(key,jp);
		}
		
		public String buildWholeString() {
			return String.join(" ", buildStringList());
		}
		
		public List<String> buildStringList() {
			if (!pairOptions.getPmap().containsKey("-crawlId")) {
				throw new RuntimeException("crawlId jobparameter is required!");
			}
			List<String> all = new ArrayList<>();
			
			// the order does big matters.
			all.addAll(javaDoptions.toCommaSeparatedString());
			all.addAll(orphanOptions.toStringList());
			all.addAll(pairOptions.toStringList());
			return all;
		}
		
		private void addDefaultCommons() {
			addJavaDOption("mapred.reduce.tasks", numSlaves * 2);
			addJavaDOption("mapred.child.java.opts=-Xmx1000m");
			addJavaDOption("mapred.reduce.tasks.speculative.execution=false");
			addJavaDOption("mapred.map.tasks.speculative.execution=false");
			addJavaDOption("mapred.compress.map.output=true");
		}
	
	public class InjectJobOptionBuilder {
		public InjectJobOptionBuilder() {
			addDefault();
		}
		
		private void addDefault() {
			addCommand("inject");
		}
		
		public NutchJobOptionBuilder and() {
			return NutchJobOptionBuilder.this;
		}

		public InjectJobOptionBuilder seedDir(String seedDir) {
			addOrphanOtion(0, seedDir);
			return this;
		}
	}

	public class GenerateJobOptionBuilder {
		
		public GenerateJobOptionBuilder(String batchId) {
			addCommand("generate");
			addParameter("-topN", numSlaves * 50000);
			addOrphanOtion(10, "-noNorm");
			addOrphanOtion(20, "-noFilter");
			addOrphanOtion(30, "-noNorm");
			addParameter("-adddays", 0);
			addParameter("-batchId", batchId);
			addDefaultCommons();
		}
		
		public NutchJobOptionBuilder and() {
			return NutchJobOptionBuilder.this;
		}

		public GenerateJobOptionBuilder addDays(int addDays) {
			addParameter("-adddays", addDays);
			return this;
		}
		
		public GenerateJobOptionBuilder forceFetch() {
			return this;
		}
	}
	

	public class UpdateDbJobOptionBuilder {
	
		public UpdateDbJobOptionBuilder() {
			addCommand("updatedb");
		}
		
		public NutchJobOptionBuilder and() {
			return NutchJobOptionBuilder.this;
		}
	}
	
	
	public class ParseJobOptionBuilder {
		
		private static final String joSkipping = "mapred.skip.attempts.to.start.skipping";
		private static final String joSkipRecords = "mapred.skip.map.max.skip.records";
		
		public ParseJobOptionBuilder(String batchId) {
			addCommand("parse");
			addJavaDOption(joSkipping, 2);
			addJavaDOption(joSkipRecords, 1);
			addOrphanOtion(10, batchId);
		}
		
		public NutchJobOptionBuilder and() {
			return NutchJobOptionBuilder.this;
		}

		public ParseJobOptionBuilder startSkipping(int startSkipping) {
			addJavaDOption(joSkipping, startSkipping);
			return this;
		}
		
		/**
		 * enable the skipping of records for the parsing so that a dodgy document so that it does not fail the full task.
		 * default is: -D mapred.skip.attempts.to.start.skipping=2 -D mapred.skip.map.max.skip.records=1
		 * @param skipRecords
		 * @return
		 */
		public ParseJobOptionBuilder skipRecords(int skipRecords) {
			addJavaDOption(joSkipRecords, skipRecords);
			return this;
		}
	}
	
	public class FetchJobOptionBuilder {
		public FetchJobOptionBuilder(String batchId) {
			addCommand("fetch");
			addParameter("-timeLimitFetch", 180);
			addParameter("-threads", 50);
			addOrphanOtion(10, batchId);
		}
		
		public NutchJobOptionBuilder and() {
			return NutchJobOptionBuilder.this;
		}
		
		public FetchJobOptionBuilder timeLimitFetch(int timeLimitFetch) {
			addParameter("-timeLimitFetch", timeLimitFetch);
			return this;
		}
		
		public FetchJobOptionBuilder threads(int threads) {
			addParameter("-threads", threads);
			return this;
		}
	}
	
	
	public static String getRandomBatchId() {
		return Instant.now().getEpochSecond() + "-" + new Random().nextInt(100000);
	}
	
	private static class PairOptions {
		private final Map<String, String> pmap = new HashMap<>();
		
		public void addPair(String key, Object value) {
			pmap.put(key, String.valueOf(value));
		}
		
		public List<String> toStringList() {
			return pmap.entrySet().stream().flatMap(e -> Stream.of(e.getKey(), e.getValue())).collect(Collectors.toList());
		}

		public Map<String, String> getPmap() {
			return pmap;
		}
	}
	
	
	private class OrphanOptions {
		private List<OrphanOption> oos = new ArrayList<>();
		
		public void add(int position, String value) {
			Optional<OrphanOption> ooo = oos.stream().filter(o -> value.equals(o.getValue())).findAny();
			if (ooo.isPresent()) {
				ooo.get().setPosition(position);
				ooo.get().setValue(value);
			} else {
				oos.add(new OrphanOption(position, value));
			}
		}
		
		public List<String> toStringList() {
			Collections.sort(oos);
			return oos.stream().map(oo -> oo.getValue()).collect(Collectors.toList());
		}
	}
	
	private class OrphanOption implements Comparable<OrphanOption> {
		private int position;
		private String value;
		
		public OrphanOption(int position, String value) {
			this.position = position;
			this.value = value;
		}
		
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public int compareTo(OrphanOption o) {
			if (o.getPosition() > this.getPosition()) {
				return -1;
			} else if (o.getPosition() < this.getPosition()){
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	private class JavaDoptions {
		List<JavaDoption> jdos = new ArrayList<>();
		
		protected void addJavaDOption(String value) {
			jdos.add(new JavaDoption(value));
		}
		
		protected void addJavaDOption(String key, Object value) {
			jdos.add(new JavaDoption(key, String.valueOf(value)));
		}
		
		public List<String> toCommaSeparatedString() {
			List<String> l = new ArrayList<>();
			if (!jdos.isEmpty()) {
				l.add("NOITPOD-" + jdos.stream().map(jdo -> jdo.getKey() + "=" + jdo.getValue()).collect(Collectors.joining(",")));
			}
			return l;
		}
		
		public List<String> toPowershellMulitpleParams() {
			List<String> l = new ArrayList<>();
			if (!jdos.isEmpty()) {
				l.add("-D");
				l.add(jdos.stream().map(jdo -> jdo.getKey() + "=" + jdo.getValue()).collect(Collectors.joining(",")));
			}
			return l;
		}
		
		public List<String> toStringList() {
			List<String> l = new ArrayList<>();
			for(JavaDoption jdo : jdos) {
				l.add("-D");
				l.add(jdo.getKey() + "=" + jdo.getValue());
			}
			return l;
		}
		
		public String toString() {
			return jdos.stream().map(jdo -> "-D " + jdo.getKey() + "=" + jdo.getValue()).collect(Collectors.joining(" "));
		}

	}
	
	private class JavaDoption {
		private String key;
		private String value;
		
		public JavaDoption(String kv) {
			int i = kv.lastIndexOf('=');
			this.key = kv.substring(0, i);
			this.value = kv.substring(i + 1);
		}
		
		public JavaDoption(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}
		public String getValue() {
			return value;
		}
	}
}
