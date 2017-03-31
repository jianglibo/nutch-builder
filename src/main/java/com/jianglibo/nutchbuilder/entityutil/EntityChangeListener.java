package com.jianglibo.nutchbuilder.entityutil;

import java.nio.file.Files;
import java.nio.file.Path;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
//import org.springframework.data.rest.core.event.BeforeCreateEvent;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;
import com.jianglibo.nutchbuilder.exception.BuilderTemplateFolderBeyondBoundaryException;
import com.jianglibo.nutchbuilder.exception.BuilderTemplateFolderMissingException;
import com.jianglibo.nutchbuilder.exception.NutchBuilderException;

@Component
public class EntityChangeListener {
	
	static ApplicationConfig applicationConfig;
	
	@Autowired
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		EntityChangeListener.applicationConfig = applicationConfig;
	}
	
	/**
	 * BeforeCreateEvent
	 * BeforeCreateEnvt
	 * @param bce
	 * @throws NutchBuilderException
	 */
	
//	@EventListener
//	public void touchForCreate(BeforeCreateEvent bce) throws NutchBuilderException {
//		Object target = bce.getSource();
//		if (target instanceof NutchBuilderTemplate) {
//			NutchBuilderTemplate nbt = (NutchBuilderTemplate) target;
//			Path rp = applicationConfig.getTemplateRootPath();
//			if (!Files.exists(rp.resolve(nbt.getName()))) {
//				throw new BuilderTemplateFolderMissingException();
//			}
//			if (!rp.resolve(nbt.getName()).normalize().startsWith(rp)) {
//				throw new BuilderTemplateFolderBeyondBoundaryException();
//			}
//		}
//	}
}
