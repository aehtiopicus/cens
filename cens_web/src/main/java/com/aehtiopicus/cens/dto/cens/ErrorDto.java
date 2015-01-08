package com.aehtiopicus.cens.dto.cens;

import org.springframework.http.HttpStatus;

public class ErrorDto {

	private String errorMessage;
	private HttpStatus statusCode;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
