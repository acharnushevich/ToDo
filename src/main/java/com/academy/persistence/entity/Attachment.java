package com.academy.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"task"})
@ToString(exclude = {"task"})

@Entity
@Table(name = "ATTACHMENTS")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "file_name")
    private String fileName;

    @Lob
    @Column (name = "file_data")
    private Blob fileData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "ID")
    private Task task;

    public void addTasks(Task task) {
        this.setTask(task);
        task.getAttachments().add(this);
    }
}