package com.jianglibo.nutchbuilder.katharsis.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapper;

@Component
public class DataIntegrityViolationExceptionMapper implements ExceptionMapper<DataIntegrityViolationException> {

	public static final int APP_ERROR_STATUS_CODE = 500;

	@Override
	public ErrorResponse toErrorResponse(DataIntegrityViolationException e) {
		if (e.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
			Throwable tw = cve.getCause();
			ErrorData ed = ErrorData.builder().setTitle(e.getMessage()).setCode(String.valueOf(cve.getErrorCode())).setDetail(tw.getMessage()).build();
			return ErrorResponse.builder().setStatus(APP_ERROR_STATUS_CODE)
			.setSingleErrorData(ed).build();
		}
		return null;
	}

	@Override
	public DataIntegrityViolationException fromErrorResponse(ErrorResponse errorResponse) {
		ErrorData ed =  errorResponse.getErrors().iterator().next();
		DataIntegrityViolationException ae = new DataIntegrityViolationException(ed.getDetail());
		return ae;
	}

	@Override
	public boolean accepts(ErrorResponse errorResponse) {
		return errorResponse.getHttpStatus() == APP_ERROR_STATUS_CODE;
	}

}
