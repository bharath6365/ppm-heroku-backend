package com.bharath.projectmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bharath.projectmanagement.domain.AppUser;
import com.bharath.projectmanagement.exceptions.UsernameExistsException;
import com.bharath.projectmanagement.repositories.AppUserRepository;

@Service
public class AppUserService {
	
	@Autowired
	private AppUserRepository userRepository;
	
	@Autowired
	// Password Hashing.
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AppUser saveUser(AppUser newUser) {
		try {
			
			// What do you need to do?
			
			// Username is unique
			
			// Password and confirm password match.
			
			// Do not show the confirm password.
			
			// Encrypt the password.
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			
			return userRepository.save(newUser);
			
		} catch (Exception e) {
			
			throw new UsernameExistsException("Username already exists");
		}		
  }
  
  public AppUser getUserByName(String username) {
    return userRepository.findByUsername(username);
  }

}
