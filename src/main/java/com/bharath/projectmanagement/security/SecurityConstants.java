package com.bharath.projectmanagement.security;

public class SecurityConstants {
  public static final String SIGNUP_URL = "/api/users/**";
  public static final String TOKEN_SECRET = "JWTSecSignOffSecret31738121";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  // Setting Token Expiry to 1 day.
  public static final long TOKEN_EXPIRATION_TIME = 86400000;
  
}