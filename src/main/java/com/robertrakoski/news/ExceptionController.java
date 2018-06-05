package com.robertrakoski.news;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionController {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorMessage> handleException(Exception exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), exception.getClass().getName());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	static class ErrorMessage {
		private String message;
		private String errorClass;

		ErrorMessage(String message, String errorClass) {
			this.message = message;
			this.errorClass = errorClass;
		}

		public String getMessage() {
			return message;
		}

		public String getErrorClass() {
			return errorClass;
		}
	}
}