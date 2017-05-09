package com.jianglibo.nutchbuilder.facade;

public class SortBroker {
	private String fieldName;
	private boolean ascending;
	
	public SortBroker(String fieldName, boolean ascending) {
		super();
		this.fieldName = fieldName;
		this.ascending = ascending;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public boolean isAscending() {
		return ascending;
	}
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
	
	
}
