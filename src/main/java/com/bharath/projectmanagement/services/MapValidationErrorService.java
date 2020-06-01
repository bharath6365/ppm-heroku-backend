package com.bharath.projectmanagement.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {
	
	public ResponseEntity<?> mapValidationService(BindingResult result) {
//			  Extract values from getFieldErrors. Needs to be converted as a bean.
			  Map<String, String> errorMap = new HashMap<>();
			  
			  for(FieldError error: result.getFieldErrors()) {
				  /*
				   * 
				   * getField() => Field name
				   * getDefautMessage() => Field Error Message
				   */
				  errorMap.put(error.getField(), error.getDefaultMessage());
			  }
			  
			  return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
			  
	}

}
