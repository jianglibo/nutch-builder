package com.jianglibo.nutchbuilder.hbaserest;

import java.util.List;
import java.util.Map;

public class HbaseTableSchema {
	
	private final Map<String, Object> rawResponse;
	
	public HbaseTableSchema(Map<String, Object> rawResponse) {
		this.rawResponse = rawResponse;
	}

	public String getName() {
		return (String) rawResponse.get("name");
	}

	public List<Map<String, Object>> getColumnSchema() {
		return (List<Map<String, Object>>) rawResponse.get("ColumnSchema");
	}

	public boolean isMeta() {
		String metas = (String) rawResponse.get("IS_META");
		if ("true".equalsIgnoreCase(metas)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getOtherValue(String key) {
		return (String) rawResponse.get(key);
	}
	
}
