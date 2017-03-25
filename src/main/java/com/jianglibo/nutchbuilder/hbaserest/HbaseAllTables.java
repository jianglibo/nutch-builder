package com.jianglibo.nutchbuilder.hbaserest;

import java.util.ArrayList;
import java.util.List;

public class HbaseAllTables {

	public List<HbaseTableName> table = new ArrayList<>();
	
	public List<HbaseTableName> getTable() {
		return table;
	}

	public void setTable(List<HbaseTableName> table) {
		this.table = table;
	}

	public static class HbaseTableName {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return "name: " + getName();
		}
	}
}
