package com.academy.persistence.repositories;

import com.academy.persistence.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @EntityGraph(attributePaths = "task")
    @Query("from Activity a where a.user.id = :id order by a.date asc")
    List<Activity> findAllTaskByUserId(@Param("id") Long id, Pageable pageable);
}
