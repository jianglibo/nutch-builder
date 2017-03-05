package com.jianglibo.nutchbuilder.exception;

public class BuilderTemplateFolderBeyondBoundaryException extends NutchBuilderException {

	private static final long serialVersionUID = 1L;
	
	private static final int code = 1002;

	public BuilderTemplateFolderBeyondBoundaryException() {
		super(code, "templateBeyondRoot","It means configurated template root plus template name is not a child of tempate root.");
	}
}
