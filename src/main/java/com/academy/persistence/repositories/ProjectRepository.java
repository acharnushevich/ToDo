package com.academy.persistence.repositories;

import com.academy.persistence.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    @EntityGraph(attributePaths = "tasks")
    @Query("from Project p where p.id = :id")
    Project findFullById(@Param("id") Long id);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);
}
