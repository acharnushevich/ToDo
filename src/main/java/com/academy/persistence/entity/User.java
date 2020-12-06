package com.academy.persistence.entity;

import com.academy.persistence.entity.enums.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tasks", "workLogs", "activities"})
@ToString(exclude = {"tasks", "workLogs", "activities"})

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String description;

    @Column (name = "projects_id")
    private String projectsId;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column (name = "profile_enable")
    private Boolean profileEnable;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<WorkLog> workLogs = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Activity> activities = new HashSet<>();

    public void addTasks(Task task) {
        this.tasks.add(task);
        task.setUser(this);
    }

    public void addWorkLogs(WorkLog workLog) {
        this.workLogs.add(workLog);
        workLog.setUser(this);
    }

    public void addActivities(Activity activity) {
        this.activities.add(activity);
        activity.setUser(this);
    }
}
