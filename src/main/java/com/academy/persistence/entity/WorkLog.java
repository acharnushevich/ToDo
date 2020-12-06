package com.academy.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"user", "task"})
@ToString(exclude = {"user", "task"})

@Entity
@Table(name = "WORK_LOGS")
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long time;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "ID")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private User user;

    public void addTasks(Task task) {
        this.setTask(task);
        task.getWorkLogs().add(this);
    }

    public void addUsers(User user) {
        this.setUser(user);
        user.getWorkLogs().add(this);
    }
}
