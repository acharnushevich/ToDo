package com.academy.persistence.repositories;

import com.academy.persistence.entity.WorkLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    @EntityGraph(attributePaths = "user")
    @Query("from WorkLog w where w.task.id = :id")
    List<WorkLog> findAllUserByTaskId(@Param("id") Long id);
}
