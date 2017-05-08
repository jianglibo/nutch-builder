package com.jianglibo.nutchbuilder.katharsis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.InvalidClaimException;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapper;

@Component
public class InvalidClaimExceptionMapper implements ExceptionMapper<InvalidClaimException> {

	@Override
	public ErrorResponse toErrorResponse(InvalidClaimException e) {
		ErrorData ed = ErrorData.builder().setTitle(InvalidClaimException.class.getName()).setDetail(e.getMessage()).build();
		return ErrorResponse.builder().setStatus(HttpStatus.BAD_REQUEST.value())
		.setSingleErrorData(ed).build();
	}

	@Override
	public InvalidClaimException fromErrorResponse(ErrorResponse errorResponse) {
		ErrorData ed =  errorResponse.getErrors().iterator().next();
		InvalidClaimException ae = new InvalidClaimException(ed.getDetail()) {
		};
		return ae;
	}

	@Override
	public boolean accepts(ErrorResponse errorResponse) {
		return errorResponse.getHttpStatus() == HttpStatus.BAD_REQUEST.value();
	}

}
