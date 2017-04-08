package com.jianglibo.nutchbuilder.katharsis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapper;

@Component
public class AccessDeniedExceptionMapper implements ExceptionMapper<AuthenticationException> {

	@Override
	public ErrorResponse toErrorResponse(AuthenticationException e) {
		ErrorData ed = ErrorData.builder().setTitle(AuthenticationException.class.getName()).setDetail(e.getMessage()).build();
		return ErrorResponse.builder().setStatus(HttpStatus.BAD_REQUEST.value())
		.setSingleErrorData(ed).build();
	}

	@Override
	public AuthenticationException fromErrorResponse(ErrorResponse errorResponse) {
		ErrorData ed =  errorResponse.getErrors().iterator().next();
		AuthenticationException ae = new AuthenticationException(ed.getDetail()) {
		};
		return ae;
	}

	@Override
	public boolean accepts(ErrorResponse errorResponse) {
		return errorResponse.getHttpStatus() == HttpStatus.BAD_REQUEST.value();
	}

}
