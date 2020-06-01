package com.bharath.projectmanagement.web;

import javax.validation.Valid;

import com.bharath.projectmanagement.domain.AppUser;
import com.bharath.projectmanagement.security.JWTTokenProvider;
import com.bharath.projectmanagement.services.AppUserService;
import com.bharath.projectmanagement.services.MapValidationErrorService;
import com.bharath.projectmanagement.validation.AppUserValidator;
import static com.bharath.projectmanagement.security.SecurityConstants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import payload.LoginRequest;
import payload.LoginResponse;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private MapValidationErrorService errorService;

  @Autowired
  private AppUserValidator appUserValidator;

  @Autowired
  private JWTTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticatonManager;

  @PostMapping("/register")

  public ResponseEntity<?> registerUser(@Valid @RequestBody AppUser newUser, BindingResult result) {

    // Password and confirm password check.
    appUserValidator.validate(newUser, result);

    if (result.hasErrors()) {
      return errorService.mapValidationService(result);
    }

    appUserService.saveUser(newUser);

    return new ResponseEntity<AppUser>(newUser, HttpStatus.OK);

  }

  @PostMapping("/login")
  
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
    if (result.hasErrors()) {
      return errorService.mapValidationService(result);
    }
    
    // Hey Authenticator here's the username and the password.

    // .authenticate() will internally call our UserService to authenticate given username and password.
    Authentication authentication = authenticatonManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(),
        loginRequest.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwtToken = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new LoginResponse(true, jwtToken));
  }

}
