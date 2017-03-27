package com.jianglibo.nutchbuilder.exception;

public class HbaseSiteXmlException extends NutchBuilderException {

	private static final long serialVersionUID = 1L;
	
	private static final int code = 2001;

	public HbaseSiteXmlException(String shortMsg, String longMsg) {
		super(code, shortMsg, longMsg);
	}
}
