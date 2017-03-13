package com.jianglibo.nutchbuilder.nutchalter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GoraProperties {
	
	static Pattern datastorePtn = Pattern.compile("gora\\.datastore\\.default="); 
	
	protected static List<String> alterLines(Path properties, StoreType storeType) {
		List<String> newlines = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(properties);
			boolean found = false;
			for(String line: lines) {
				if (datastorePtn.matcher(line).find()) {
					if (found) {
						noop();
					} else {
						found = true;
						switch (storeType) {
						case HBaseStore:
							newlines.add("gora.datastore.default=org.apache.gora.hbase.store.HBaseStore");
							break;
						default:
							break;
						}
					}
				} else {
					newlines.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newlines;
	}
	
	public static void alterGoraProperties(Path projectRoot, StoreType storeType) throws IOException {
		Path pf = projectRoot.resolve("conf/gora.properties");
		Files.write(pf, alterLines(pf, storeType));
	}
	
	private static void noop(){}
	
	public static enum StoreType {
		HBaseStore,MockDataStore,
	}
}
