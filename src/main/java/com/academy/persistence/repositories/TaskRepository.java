package com.academy.persistence.repositories;

import com.academy.persistence.entity.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @EntityGraph(attributePaths = {"user", "project"})
    @Query("from Task t where t.id = :id")
    Task findUsersAndProjectsById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"user", "project"})
    @Query("from Task t where t.project.id = :projectId")
    List<Task> findAllUsersAndProjectsById(@Param("projectId") Long projectId);

    @EntityGraph(attributePaths = {"user", "project", "attachments", "workLogs", "activities"})
    @Query("from Task t where t.id = :id")
    Task findFullById(@Param("id") Long id);
}
