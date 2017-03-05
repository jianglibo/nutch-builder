package com.jianglibo.nutchbuilder.exception;

public class ForClientJson {

	private final int code;
	private final String shortmsg;
	private final String msg;
	
	
	public ForClientJson(NutchBuilderException nbe) {
		this.code = nbe.getCode();
		this.shortmsg = nbe.getShortmsg();
		this.msg = nbe.getMessage();
	}
	
	
	public int getCode() {
		return code;
	}
	public String getShortmsg() {
		return shortmsg;
	}
	public String getMsg() {
		return msg;
	}
	
	
}
