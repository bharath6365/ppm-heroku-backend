package com.bharath.projectmanagement.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Backlog {
	
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  // Explanation in Project Task.
  private int PTSEQUENCE = 0;
  
  private String projectIdentifier;
  
  // OneToOne Mapping with Project
  
  // project should match mappedBy
  @OneToOne(fetch = FetchType.EAGER)
  // The column that should be created on the table.
  @JoinColumn(name="project_id", nullable = false)
  @JsonIgnore
  private Project project;
  
  // OneToMany Mapping with Project Tasks
  @OneToMany
  (
   cascade = CascadeType.REFRESH,
   fetch = FetchType.EAGER,
   mappedBy = "backlog", 
   orphanRemoval = true
   )
  private List<ProjectTask> projectTasks = new ArrayList<>();
  
  public Backlog() {
	  
  }

public Project getProject() {
	return project;
}

public void setProject(Project project) {
	this.project = project;
}

// Getters and setters.
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public int getPTSEQUENCE() {
	return PTSEQUENCE;
}

public void setPTSEQUENCE(int pTSEQUENCE) {
	PTSEQUENCE = pTSEQUENCE;
}

public String getProjectIdentifier() {
	return projectIdentifier;
}

public void setProjectIdentifier(String projectIdentifier) {
	this.projectIdentifier = projectIdentifier;
}
  
  
}
