package com.bharath.projectmanagement.services;

import com.bharath.projectmanagement.domain.Backlog;
import com.bharath.projectmanagement.domain.Project;
import com.bharath.projectmanagement.domain.ProjectTask;
import com.bharath.projectmanagement.exceptions.ProjectIdentifierNotFoundException;
import com.bharath.projectmanagement.repositories.BacklogRepository;
import com.bharath.projectmanagement.repositories.ProjectRepository;
import com.bharath.projectmanagement.repositories.ProjectTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

  @Autowired
  private BacklogRepository backlogRepository;

  @Autowired
  private ProjectTaskRepository projectTaskRepository;

  @Autowired
  private ProjectService projectService;

  public Iterable<ProjectTask> findAllTasks(String projectIdentifier, String username) {

    // Let's get a handle of the project first.
    Project project = projectService.findProjectByIndetifier(projectIdentifier, username);
    return projectTaskRepository.findByProjectIdentifierOrderByPriority(project.getProjectIdentifier());
  }

  public Object addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
    // Ok.Before we start working with the backlog. Let's find out the project based on backlog.
    Project project = projectService.findProjectByIndetifier(projectIdentifier, username);

    // Find the backlog from the project.
    Backlog backlog = project.getBacklog();

    if (backlog == null) {
      throw new ProjectIdentifierNotFoundException("Project Identifier not found");
    }

    // Associate projecttask with the backlog.
    projectTask.setBacklog(backlog);

    // Get the project sequence integer
    int sequenceInteger = backlog.getPTSEQUENCE();
    ++sequenceInteger;
    backlog.setPTSEQUENCE(backlog.getPTSEQUENCE() + 1);

    // Get the String version
    String sequence = projectIdentifier + "-" + sequenceInteger;

    // Set the sequence to the project task.
    projectTask.setProjectSeqeunce(sequence);

    // Add the project identifier to the project task

    projectTask.setProjectIdentifier(projectIdentifier);

    return projectTaskRepository.save(projectTask);
  }

  public Object getTask(String projectSequence, String projectIdentifier, String username) {
    // First check is to see if the project exists.
    Project project = projectService.findProjectByIndetifier(projectIdentifier, username);

    Backlog backlog = project.getBacklog();

    if (backlog == null) {
      throw new ProjectIdentifierNotFoundException("Project Identifier not found");

    }

    ProjectTask task = projectTaskRepository.findByProjectSeqeunce(projectSequence);

    if (task == null) {
      throw new ProjectIdentifierNotFoundException("Project Sequence not found");
    }

    return task;
  }

  public ProjectTask updateTask(ProjectTask incomingTask, String projectIdentifier, String username) {
    // Make sure that incoming project Identifier exists.
    // Reusing get task function due to repetitive logic.
    String projectSequence = incomingTask.getProjectSeqeunce();

    ProjectTask task = (ProjectTask) getTask(projectSequence, projectIdentifier, username);

    if (task == null) {
      throw new ProjectIdentifierNotFoundException("Task Sequence Incorrect");
    }
    task = incomingTask;

    return projectTaskRepository.save(task);

  }

  public void deleteTask(String projectIdentifier, String projectSequence, String username) {
    // This will handle validations 😉
    ProjectTask task = (ProjectTask) getTask(projectSequence, projectIdentifier, username);

    projectTaskRepository.delete(task);
  }

}
