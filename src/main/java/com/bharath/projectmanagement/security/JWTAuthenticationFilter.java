package com.bharath.projectmanagement.security;

import static com.bharath.projectmanagement.security.SecurityConstants.HEADER_STRING;
import static com.bharath.projectmanagement.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bharath.projectmanagement.domain.AppUser;
import com.bharath.projectmanagement.services.CustomAppUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTTokenProvider jwtTokenProvider;

  @Autowired
  private CustomAppUserDetailService customAppUserDetailService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      // Get the token from the request.
      String tokenWithBearer = request.getHeader(HEADER_STRING);
      String token = "";

      // Run substring and get everything after Bearer
      if (tokenWithBearer.startsWith(TOKEN_PREFIX)) {
        token = tokenWithBearer.substring(7, tokenWithBearer.length());
      }

      // Once we have the token validate the user.
      if (jwtTokenProvider.validateToken(token)) {
        Long userId = jwtTokenProvider.getUserFromToken(token);
        AppUser appUser = customAppUserDetailService.loadUserById(userId);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(appUser, null,
            Collections.emptyList());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Build the context like we do in the login controller.
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

    } catch (Exception e) {
      logger.error("Cannot set user context in the filter" + e.getMessage());
    }

    // Call the next middleware in place. Just like the next() method.
    filterChain.doFilter(request, response);

  }

}