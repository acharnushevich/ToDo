package com.academy.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"task", "user"})
@ToString(exclude = {"task", "user"})

@Entity
@Table(name = "ACTIVITIES")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    public void addTasks(Task task) {
        this.setTask(task);
        task.getActivities().add(this);
    }

    public void addUsers(User user) {
        this.setUser(user);
        user.getActivities().add(this);
    }
}
