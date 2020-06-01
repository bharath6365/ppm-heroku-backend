package com.bharath.projectmanagement.exceptions;

public class ProjectIDExceptionResponse {
	
	// This will be the key in the JSON and the value will be the message in the constructor.
	private String projectIdentifier;
	
	

	public ProjectIDExceptionResponse(String projectIdentifier) {
		super();
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

}
