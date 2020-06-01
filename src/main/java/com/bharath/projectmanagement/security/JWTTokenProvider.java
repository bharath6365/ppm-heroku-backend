package com.bharath.projectmanagement.security;

import static com.bharath.projectmanagement.security.SecurityConstants.TOKEN_EXPIRATION_TIME;
import static com.bharath.projectmanagement.security.SecurityConstants.TOKEN_SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bharath.projectmanagement.domain.AppUser;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
// This class will handle interactions with creating, reading from a JWT.
public class JWTTokenProvider {
  // Generate the token

  public String generateToken(Authentication authentication) {

    // Get the incoming user object.
    AppUser user = (AppUser) authentication.getPrincipal();
    
    // We need these date values on our token.
    Date currentDate = new Date(System.currentTimeMillis());

    Date expiryDate = new Date(currentDate.getTime() + TOKEN_EXPIRATION_TIME);

    String userId = Long.toString(user.getId());

    Map<String, Object> userClaims = new HashMap<>();

    // What information should we store in the token?
    userClaims.put("id", userId);
    userClaims.put("username", user.getUsername());
    userClaims.put("fullname", user.getFullName());
    // If you introduce roles in the future include them here.

    return Jwts.builder().setSubject(userId)
        // Information about the user.
        .setClaims(userClaims).setIssuedAt(currentDate).setExpiration(expiryDate)
        // Add a signature for our JWT.
        .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();

  }

  // Validate the tokens
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token);

      return true;
    } catch(Exception e) {
      System.out.println("Invalid JWT Token" + e.getMessage());

      return false;
    }
  }

  // Get the user id from the token.
  public Long getUserFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();

    String id = (String) claims.get("id");

    return Long.parseLong(id);
  }
}