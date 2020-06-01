package com.bharath.projectmanagement.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/* Every project task will have its own id in the database. But when we display it to the
	 * user we wanna make sure we show the project identifier + the count from the backlog.
	 * 
	 *  For example we have a project with identifier XFVW. The first task created under it would 
	 *  have the sequence XFVW1 and the next sequence will be XFVW2. That's why we have the PTSEQUENCE
	 *  in the Backlog table. This will be set automatically. User shouldn't change this.
	*/
	
	@Column(updatable = false)
	private String projectSeqeunce; 
	
	// Project Description / Summray
	@NotBlank(message = "Task Summary is required")
	private String summary;
	
	// Summary is just the title of the task. This would be the detailed description. 
	private String detailedDescription;
	
	public String getDetailedDescription() {
		return detailedDescription;
	}

	public void setDetailedDescription(String detailedDescription) {
		this.detailedDescription = detailedDescription;
	}



	// The current status of the project
	@Column(length = 32, columnDefinition = "varchar(12) default 'TODO'")
	@Enumerated(EnumType.STRING)
	private StatusType status = StatusType.TODO;
	


	// Tasks will be grouped based on priority.
	@Column(length = 32, columnDefinition = "varchar(12) default 'LOW'")
	@Enumerated(EnumType.STRING)
	private PriorityType priority = PriorityType.LOW;
	
	// Many to one with Backlogs.
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}



	// Due date for the project
	private Date dueDate;
	
	// ManyToOne
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;
	
	
	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}



	@JsonFormat(pattern="yyyy-mm-dd")
	private Date created_At;
	
	@JsonFormat(pattern="yyyy-mm-dd")
	private Date updated_At;
	
	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}
	
	// Whenever we update the project. Update updated_At
	@PreUpdate
	protected void onUpdate() {
	this.updated_At = new Date();
	}
	
	// Without no arg constructors there are issues with few versions of spring
    public ProjectTask() {
    	
    }
    
    // Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSeqeunce() {
		return projectSeqeunce;
	}

	public void setProjectSeqeunce(String projectSeqeunce) {
		this.projectSeqeunce = projectSeqeunce;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}


	public StatusType getStatus() {
		return status;
	}
    
	

	public PriorityType getPriority() {
		return priority;
	}

	public void setPriority(PriorityType priority) {
		this.priority = priority;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	
	
	// <!-- End Getters and Setters --> //
	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSeqeunce=" + projectSeqeunce + ", summary=" + summary + ", status="
				+ status + ", priority=" + priority + ", projectIdentifer=" + projectIdentifier + ", dueDate=" + dueDate
				+ ", created_At=" + created_At + ", updated_At=" + updated_At + "]";
	}
    
	private enum StatusType{
		TODO,
		INPROGRESS,
		DONE
	}
	
	private enum PriorityType{
		LOW,
		HIGH,
		MEDIUM
	}
}


