package com.jianglibo.nutchbuilder.katharsis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapper;

@Component
public class UnsortableExceptionMapper implements ExceptionMapper<UnsortableException> {

	@Override
	public ErrorResponse toErrorResponse(UnsortableException e) {
		ErrorData ed = ErrorData
				.builder()
				.setTitle(e.getTitle())
				.setCode(e.getCode())
				.setDetail(e.getDetail())
				.build();
		return ErrorResponse.builder().setStatus(HttpStatus.BAD_REQUEST.value())
		.setSingleErrorData(ed).build();
	}

	@Override
	public UnsortableException fromErrorResponse(ErrorResponse errorResponse) {
		ErrorData ed =  errorResponse.getErrors().iterator().next();
		UnsortableException ae = new UnsortableException(ed.getDetail());
		return ae;
	}

	@Override
	public boolean accepts(ErrorResponse errorResponse) {
		return errorResponse.getHttpStatus() == HttpStatus.BAD_REQUEST.value();
	}

}
