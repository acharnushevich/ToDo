package com.academy.persistence.repositories;

import com.academy.persistence.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("from Attachment a where a.task.id = :id order by a.fileName asc")
    List<Attachment> findAllByTaskId(@Param("id") Long id);
}
