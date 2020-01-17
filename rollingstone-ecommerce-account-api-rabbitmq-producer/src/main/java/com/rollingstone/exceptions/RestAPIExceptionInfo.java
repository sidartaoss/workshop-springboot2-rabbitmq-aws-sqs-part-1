package com.rollingstone.exceptions;

public class RestAPIExceptionInfo {

	private final String message;
	private final String cause;

	public RestAPIExceptionInfo() {
		this.message = null;
		this.cause = null;
	}
	
	public RestAPIExceptionInfo(String message, String cause) {
		this.message = message;
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public String getCause() {
		return cause;
	}

	
}
