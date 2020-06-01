package com.bharath.projectmanagement.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {

	// Unique identifier
	@Id
//	Tells hibernate to auto increment the ID.
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	// Tells JPA to reject this if project name is not filled.
    @NotBlank(message="Project name is required.")
	private String projectName;

	// Custom identifier for users. Will be dispayed on task names to link it to a
	// project. ID doens't make sense. eg XYZ1
    @NotBlank(message="Project Identifier is required.")
    @Size(min=4, max =5, message="Please use 4-5 characters")
    
    // @Column brings us Database level validations.
    @Column(unique=true, updatable=false)
    
	private String projectIdentifier;
    
    @NotBlank(message="Project Description is required")
	private String projectDescription;

    // We can define custom patterns for DATES.
    @JsonFormat(pattern="yyyy-mm-dd")
	private Date start_date;
    
    @JsonFormat(pattern="yyyy-mm-dd")
	private Date end_date;

    @JsonFormat(pattern="yyyy-mm-dd")
    private Date created_At;

    @JsonFormat(pattern="yyyy-mm-dd")
    private Date updated_At;
    
    // User relationship
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.REFRESH)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private AppUser user;
    
    @NotBlank(message="Project Owner is required")
    private String projectOwner;
    
    // Backlog
    // When I fetch the project get me the backlog,
    // When I delete the project automatically delete the back logs.
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy ="project")
    // I don't want this in the API Response
    @JsonIgnore
    private Backlog backlog;

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

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

	public Project() {

	}

	// Getters and Setters.

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

    public AppUser getUser() {
      return user;
    }

    public void setUser(AppUser user) {
      this.user = user;
    }

    public String getProjectOwner() {
      return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
      this.projectOwner = projectOwner;
    }

}
