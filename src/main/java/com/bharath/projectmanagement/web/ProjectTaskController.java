package com.bharath.projectmanagement.web;

import java.security.Principal;

import javax.validation.Valid;

import com.bharath.projectmanagement.domain.ProjectTask;
import com.bharath.projectmanagement.services.MapValidationErrorService;
import com.bharath.projectmanagement.services.ProjectTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/backlog")
public class ProjectTaskController {

  @Autowired
  private ProjectTaskService projectTaskService;

  @Autowired
  private MapValidationErrorService errorService;

  @PostMapping("/{projectIdentifier}")
  public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask task, BindingResult result,
      @PathVariable String projectIdentifier, Principal principal) {

    // Handle Exceptions
    if (result.hasErrors()) {
      return errorService.mapValidationService(result);
    }

    Object projectTaskDB = projectTaskService.addProjectTask(projectIdentifier, task, principal.getName());

    return new ResponseEntity<Object>(projectTaskDB, HttpStatus.OK);
  }

  @GetMapping("/{projectIdentifier}")
  public Iterable<ProjectTask> getAllTasks(@PathVariable String projectIdentifier, Principal principal) {
    return projectTaskService.findAllTasks(projectIdentifier, principal.getName());
  }

  @GetMapping("/{projectIdentifier}/{projectSequence}")
  public ResponseEntity<Object> getProjectTask(@PathVariable String projectSequence,
      @PathVariable String projectIdentifier, Principal principal) {
    Object task = projectTaskService.getTask(projectSequence, projectIdentifier, principal.getName());
    return new ResponseEntity<Object>(task, HttpStatus.OK);
  }

  @PatchMapping("/{projectIdentifier}")
  public ResponseEntity<?> updateTask(@Valid @RequestBody ProjectTask incomingTask, BindingResult result,
      @PathVariable String projectIdentifier, Principal principal) {

    // Handle Exceptions
    if (result.hasErrors()) {
      return errorService.mapValidationService(result);
    }

    // Get the updated project task.
    ProjectTask updatedProjectTask = projectTaskService.updateTask(incomingTask, projectIdentifier,
        principal.getName());

    return new ResponseEntity<>(updatedProjectTask, HttpStatus.OK);
  }

  @DeleteMapping("/{projectIdentifier}/{projectSequence}")
  public ResponseEntity<String> deleteProjecTask(@PathVariable String projectSequence,
      @PathVariable String projectIdentifier, Principal principal) {
    projectTaskService.deleteTask(projectIdentifier, projectSequence, principal.getName());
    return new ResponseEntity<String>("Deleted", HttpStatus.OK);
  }

}
