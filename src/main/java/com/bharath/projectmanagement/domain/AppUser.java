package com.bharath.projectmanagement.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
// This is not a normal entity it needs to implement UserService.
// By default Spring will search for username, password fields in this entity
// and validate it with a mechanism specified in the AuthenticationBuilder
public class AppUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Email(message = "Username needs to be an email")
  @NotBlank(message = "Username is required")
  @Column(unique = true)
  private String username;

  @NotBlank(message = "Please enter your full name")
  private String fullName;

  @NotBlank(message = "Password is required")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  // Not gonna persist this on the database.
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String confirmPassword;

  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date created_At;

  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date updated_At;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<Project>();

  // Whenever we create a new object. Assign that to created_At
  @PrePersist
  protected void onCreate() {
    this.created_At = new Date();
  }

  // Whenever we update the project. Update updated_At
  @PreUpdate
  protected void onUpdate() {
    this.updated_At = new Date();
  }

  public AppUser() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public Date getCreated_At() {
    return created_At;
  }

  public void setCreated_At(Date created_At) {
    this.created_At = created_At;
  }

  public Date getUpdated_At() {
    return updated_At;
  }

  public void setUpdated_At(Date updated_At) {
    this.updated_At = updated_At;
  }

  /*
   * User Details Methods.
   * 
   *
   */

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // For Role Based PErmissions
    return null;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return true;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

}
