package com.jianglibo.nutchbuilder.exception;

import java.lang.reflect.UndeclaredThrowableException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class DomainExceptionResolver  extends  AbstractHandlerExceptionResolver {

    // package visibility for tests
    HandlerMethodReturnValueHandler responseProcessor;

    // package visibility for tests
    HandlerMethodReturnValueHandler fallbackResponseProcessor;
    
    
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		if (ex instanceof UndeclaredThrowableException) {
			UndeclaredThrowableException ute = (UndeclaredThrowableException) ex;
			Throwable tb = ute.getUndeclaredThrowable();
			if (tb instanceof BuilderTemplateFolderMissingException) {
				MappingJackson2JsonView m2v = new MappingJackson2JsonView();
				ModelAndView mav = new ModelAndView(m2v);
				mav.setStatus(HttpStatus.CREATED);
				mav.addObject("error", ((NutchBuilderException)tb).convert2ForClientJson());
				return mav;
			}
		}
		return null;
	}

}
