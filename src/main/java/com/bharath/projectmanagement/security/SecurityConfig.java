package com.bharath.projectmanagement.security;

import com.bharath.projectmanagement.services.CustomAppUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.bharath.projectmanagement.security.SecurityConstants.SIGNUP_URL;

@Configuration
@EnableWebSecurity

// Method based permissions on Spring Security. For the future.
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true

)

// Web Security Configure Adapter is a class that
// provides default security configurations. By overriding the methods we can
// extend it.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private CustomAppUserDetailService customAppUserDetailService;

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  // This lets us override the default behaviour of authorization .
  protected void configure(HttpSecurity http) throws Exception {
    // CSRF disabled because we are making use of JWTs.
    http.cors().and().csrf().disable()

        // Authentication Entry Point helps us configure error responses.
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()

        // Stateless we are not using cookies.

        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

        // Do not authorize reqeuests that are static assets.
        .authorizeRequests().antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
            "/**/*.html", "/**/*.css", "/**/*.js")
        .permitAll().antMatchers(SIGNUP_URL).permitAll().anyRequest().authenticated();


        // Register the filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


  }

  @Override
  // Go to see this CustomAppUserService Class for anything related to authentication.
  protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(customAppUserDetailService).passwordEncoder(passwordEncoder);
  }

  @Override
  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  
}
