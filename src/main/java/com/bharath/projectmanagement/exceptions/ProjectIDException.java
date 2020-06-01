package com.bharath.projectmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Whenever I throw this exception I want a bad request to be thrown to the user. 
 * By default it might be 500
 *  
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIDException extends RuntimeException{
  
	public ProjectIDException(String message) {
		// Pass the message to the run time exception.
		super(message);
	}
}
