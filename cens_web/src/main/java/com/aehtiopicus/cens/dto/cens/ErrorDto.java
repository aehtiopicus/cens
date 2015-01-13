package com.aehtiopicus.cens.dto.cens;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorDto {

	private boolean errorDto = true;
	private String message;
	private Map<String,String> errors;
	private HttpStatus statusCode;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	public boolean isErrorDto() {
		return errorDto;
	}
	public void setErrorDto(boolean errorDto) {
		this.errorDto = errorDto;
	}
	
	
	
}
