package com.academy.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tasks"})
@ToString(exclude = {"tasks"})

@Entity
@Table(name = "PROJECTS")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    public void addTasks(Task task) {
        this.tasks.add(task);
        task.setProject(this);
    }
}
