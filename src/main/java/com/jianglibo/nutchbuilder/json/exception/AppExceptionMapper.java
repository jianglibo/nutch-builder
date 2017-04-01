package com.jianglibo.nutchbuilder.json.exception;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapper;

@Component
public class AppExceptionMapper implements ExceptionMapper<AppException> {

	public static final int APP_ERROR_STATUS_CODE = 500;

	@Override
	public ErrorResponse toErrorResponse(AppException e) {
		return ErrorResponse.builder().setStatus(APP_ERROR_STATUS_CODE)
				.setErrorData(e.getErrors().stream()
						.map(ae -> ErrorData.builder().setStatus(String.valueOf(APP_ERROR_STATUS_CODE))
								.setTitle(ae.getTitle()).setCode(String.valueOf(ae.getCode())).build())
						.collect(Collectors.toList()))
				.build();
	}

	@Override
	public AppException fromErrorResponse(ErrorResponse errorResponse) {
		AppException ae = new AppException();
		for(ErrorData ed : errorResponse.getErrors()) {
			ae.addError(Integer.valueOf(ed.getCode()), ed.getTitle());
		}
		return ae;
	}

	@Override
	public boolean accepts(ErrorResponse errorResponse) {
		return errorResponse.getHttpStatus() == APP_ERROR_STATUS_CODE;
	}

}
