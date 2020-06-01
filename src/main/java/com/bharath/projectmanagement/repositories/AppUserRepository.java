package com.bharath.projectmanagement.repositories;

import org.springframework.data.repository.CrudRepository;

import com.bharath.projectmanagement.domain.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

  AppUser findByUsername(String username);
  
  // findById returns nullable. getById will return the user object.
  AppUser getById(Long id);
}
