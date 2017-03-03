package com.jianglibo.nutchbuilder.exception;

public class BuilderTemplateFolderMissingException extends NutchBuilderException {

	private static final long serialVersionUID = 1L;
	
	private static final int code = 1001;

	public BuilderTemplateFolderMissingException() {
		super(code, BuilderTemplateFolderMissingException.class.getSimpleName());
	}
}
