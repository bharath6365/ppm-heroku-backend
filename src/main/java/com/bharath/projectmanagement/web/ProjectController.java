package com.bharath.projectmanagement.web;

import java.security.Principal;

import javax.validation.Valid;

import com.bharath.projectmanagement.domain.Project;
import com.bharath.projectmanagement.services.MapValidationErrorService;
import com.bharath.projectmanagement.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
  
  @Autowired
  private ProjectService projectService;
  
  @Autowired
  private MapValidationErrorService errorService;
  
  @PostMapping("")
//  Post Request to the Root route defined in the Request Mapping.
 // @Valid will help us to print useful information in the case of a bad request body.
 // Binding result must immediately follow the model. WTF.
  public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal) {
	  
	 
	  if (result.hasErrors()) {
		  return errorService.mapValidationService(result);
	  }
//	  In this case project returned can either be null or the entire project itself. 
//	  Check the instance of project returned. If null was returned then there's some uncaught error.
	  Object createdProject = projectService.saveOrUpdateProject(project, principal.getName());
	  
	  if(createdProject instanceof Project ) {
		  return new ResponseEntity<Object>(createdProject, HttpStatus.OK);
	  } else {
		  return new ResponseEntity<String>("Uncaught Exception Occured", HttpStatus.BAD_GATEWAY);
	  }
	  
	  
  }
  
   // Find by Identifer will have the base URL pattern.
  @GetMapping("/{projectId}")
  public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectId, Principal principal) {
	  Project project = projectService.findProjectByIndetifier(projectId, principal.getName());
	  return new ResponseEntity<Project>(project, HttpStatus.OK);
  }
  
  
  // Find all Projects
  @GetMapping("/all")
  public Iterable<Project> findAllProjects(Principal principal) {
	  return projectService.findAllProjects(principal.getName());
  }
  
  // Delete by identifier
  @DeleteMapping("/{projectId}")
  public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId, Principal principal) {
	  projectService.deleteProjectByIndetifier(projectId, principal.getName());
	  return new ResponseEntity<String>("Success", HttpStatus.OK);
  }
  
  
}
