package com.jianglibo.nutchbuilder.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * RequiredAnnotationBeanPostProcessor
 * @author admin
 *
 */
@Component
public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {

    // simply return the instantiated bean as-is
    public Object postProcessBeforeInitialization(Object bean,
            String beanName) throws BeansException {
        return bean; // we could potentially return any object reference here...
    }

    public Object postProcessAfterInitialization(Object bean,
            String beanName) throws BeansException {
        try {
        	System.out.println("Bean '" + beanName + "' created : " + bean.toString());
		} catch (Exception e) {
			System.out.println("Bean '" + beanName + "' postprocess failed.");
		}
        return bean;
    }
    
}
