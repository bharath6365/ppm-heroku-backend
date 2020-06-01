package com.bharath.projectmanagement.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bharath.projectmanagement.domain.ProjectTask;

import antlr.collections.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
	
	Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
	
	ProjectTask findByProjectSeqeunce(String sequence);

}
