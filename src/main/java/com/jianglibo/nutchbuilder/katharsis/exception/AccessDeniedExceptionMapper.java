package com.jianglibo.nutchbuilder.katharsis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.constant.AppErrorCodes;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapper;

@Component
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

	@Override
	public ErrorResponse toErrorResponse(AccessDeniedException e) {
		ErrorData ed = ErrorData
				.builder()
				.setTitle(AccessDeniedException.class.getName())
				.setCode(AppErrorCodes.ACCESS_DENIED)
				.setDetail(e.getMessage())
				.build();
		return ErrorResponse.builder().setStatus(HttpStatus.BAD_REQUEST.value())
		.setSingleErrorData(ed).build();
	}

	@Override
	public AccessDeniedException fromErrorResponse(ErrorResponse errorResponse) {
		ErrorData ed =  errorResponse.getErrors().iterator().next();
		AccessDeniedException ae = new AccessDeniedException(ed.getDetail()) {
		};
		return ae;
	}

	@Override
	public boolean accepts(ErrorResponse errorResponse) {
		return errorResponse.getHttpStatus() == HttpStatus.BAD_REQUEST.value();
	}

}
