package com.jianglibo.nutchbuilder.entityutil;

import org.springframework.context.ApplicationEvent;

import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;

public class NutchBuilderTemplateEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NutchBuilderTemplateEvent(NutchBuilderTemplate source) {
		super(source);
	}
}
