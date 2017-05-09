package com.jianglibo.nutchbuilder.katharsis.exception;

import java.util.ArrayList;
import java.util.List;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<CodeAndTitle> errors = new ArrayList<>();
	
	public AppException addError(int code, String title, String detail) {
		errors.add(new CodeAndTitle(code, title, detail));
		return this;
	}
	
	public List<CodeAndTitle> getErrors() {
		return errors;
	}


	public static class CodeAndTitle {
		private int code;
		private String title;
		private String detail;
		
		public CodeAndTitle(int code, String title, String detail) {
			super();
			this.code = code;
			this.title = title;
			this.setDetail(detail);
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
		
		
	}

}
