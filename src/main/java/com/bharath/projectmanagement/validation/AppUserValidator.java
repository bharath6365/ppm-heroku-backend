package com.bharath.projectmanagement.validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.bharath.projectmanagement.domain.AppUser;

@Component
public class AppUserValidator implements org.springframework.validation.Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		// Want to perform validations on App User.
		return AppUser.class.equals(aClass);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		// Get the user
		AppUser appUser = (AppUser) object;
		
		// Password Length check
		
		if (appUser.getPassword().length() < 6) {
			// password must be the same as the 
			errors.rejectValue
			("password", "Length", "Password length must be atleast 6 characters");
		}
		
		
		// Password and confirm password check
		if (!appUser.getPassword().equals(appUser.getConfirmPassword())) {
			errors.rejectValue
			("confirmPassword", "Match", "Password and Confirm Password must match.");
			
		}
		
	}

}
