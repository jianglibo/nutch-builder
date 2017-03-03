package com.jianglibo.nutchbuilder.exception;

public class BuilderTemplateFolderBeyondBoundaryException extends NutchBuilderException {

	private static final long serialVersionUID = 1L;
	
	private static final int code = 1002;

	public BuilderTemplateFolderBeyondBoundaryException() {
		super(code, BuilderTemplateFolderBeyondBoundaryException.class.getSimpleName());
	}
}
