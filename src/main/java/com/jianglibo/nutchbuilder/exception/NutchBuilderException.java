package com.jianglibo.nutchbuilder.exception;

public abstract class NutchBuilderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int code;
	
	public NutchBuilderException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
