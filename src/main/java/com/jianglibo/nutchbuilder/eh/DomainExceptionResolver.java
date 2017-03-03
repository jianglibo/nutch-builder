package com.jianglibo.nutchbuilder.eh;

import java.lang.reflect.UndeclaredThrowableException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jianglibo.nutchbuilder.exception.BuilderTemplateFolderMissingException;

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
				ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
				mav.setStatus(HttpStatus.CREATED);
				mav.addObject("hello", "world");
				return mav;
			}
		}
		return null;
	}

}
