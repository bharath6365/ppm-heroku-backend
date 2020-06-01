package com.bharath.projectmanagement.services;
import com.bharath.projectmanagement.domain.AppUser;
import com.bharath.projectmanagement.repositories.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomAppUserDetailService implements UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Override
  // Let's spring authenticate the incoming user name during login.
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = appUserRepository.findByUsername(username);
    
    // Return right away before checking password if username is not null.
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    
    // This is why user implements UserDetails.
    return user;
  }
  

  // This is to verify the id passed in the JWT Token.
  // TODO: Why is transactional used?
  @Transactional
  public AppUser loadUserById(Long id) {
    AppUser user = appUserRepository.getById(id);

    // Return right away before checking password if username is not null.
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return user;
  }
}