package com.bharath.projectmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * This service will respond to exceptions that are being thrown in other controllers.
 * We need the controller advice annotation so that other controllers listen to this.
 * This is also a Rest Controller.
 * 
 */

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {
  
	@ExceptionHandler(value = {ProjectIDException.class})
	public final ResponseEntity<Object> handleProjectIdException(ProjectIDException ex, WebRequest request) {
		ProjectIDExceptionResponse exceptionResponse = new ProjectIDExceptionResponse(ex.getMessage());
		// In-case you are wondering how the key is set. Spring boot will automatically add it based on the
		// field that generated the exception. In this case exception was due to projectIdentifier.
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ProjectIdentifierNotFoundException.class})
	
	public final ResponseEntity<Object> handleIdentifierException
	(ProjectIdentifierNotFoundException ex, WebRequest request) {
		
		ProjectIdentifierNotFoundExceptionResponse identifierResponse = new 
				ProjectIdentifierNotFoundExceptionResponse(ex.getMessage());
		
		
		// Return 404.
		return new ResponseEntity<Object>(identifierResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = {UsernameExistsException.class})
	public final ResponseEntity<Object> handleUsernameException
	(UsernameExistsException ex, WebRequest request) {
		
		UsernameExistsExceptionResponse userNameResponse = 
		  new UsernameExistsExceptionResponse(ex.getMessage());
		
	   	
      
		return new ResponseEntity<Object>(userNameResponse, HttpStatus.BAD_REQUEST);
	}
	
	
}
