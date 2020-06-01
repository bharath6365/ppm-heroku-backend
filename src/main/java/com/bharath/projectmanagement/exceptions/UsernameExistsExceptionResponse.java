package com.bharath.projectmanagement.exceptions;

public class UsernameExistsExceptionResponse {
	
	// Response
	private String username;
	
	public UsernameExistsExceptionResponse(String message) {
		this.username = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
