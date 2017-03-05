package com.jianglibo.nutchbuilder.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("stackTrace")
public abstract class NutchBuilderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int code;
	
	private final String shortmsg;
	
	public NutchBuilderException(int code,String shortmsg, String msg) {
		super(msg);
		this.code = code;
		this.shortmsg = shortmsg;
	}

	public int getCode() {
		return code;
	}

	public String getShortmsg() {
		return shortmsg;
	}
	
	public ForClientJson convert2ForClientJson() {
		return new ForClientJson(this);
	}
}
