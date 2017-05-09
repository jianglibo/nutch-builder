package com.jianglibo.nutchbuilder.katharsis.exception;

import com.jianglibo.nutchbuilder.constant.AppErrorCodes;

public class UnsortableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title = "sort filed not supported.";
	private String code = AppErrorCodes.UNSORTABLE;
	private String detail;
	
	public UnsortableException(String detail) {
		super(detail);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
