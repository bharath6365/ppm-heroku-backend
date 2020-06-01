package com.bharath.projectmanagement.exceptions;

public class ProjectIdentifierNotFoundExceptionResponse {
  // This will be the key in the JSON.
  private String message;
  
  public ProjectIdentifierNotFoundExceptionResponse(String message) {
	  super();
	  this.message = message;
  }

  public String getMessage() {
	return message;
  }

  public void setMessage(String message) {
	this.message = message;
  }
  
}
