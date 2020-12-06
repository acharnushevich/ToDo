package com.academy.persistence.entity;

import com.academy.persistence.entity.enums.Priority;
import com.academy.persistence.entity.enums.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"user", "project", "attachments", "workLogs", "activities"})
@ToString(exclude = {"user", "project", "attachments", "workLogs", "activities"})

@Entity
@Table(name = "TASKS")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column (name = "task_status")
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "ID")
    private Project project;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Attachment> attachments = new HashSet<>();

    @OneToMany(mappedBy = "task", orphanRemoval = true)
    private Set<WorkLog> workLogs = new HashSet<>();

    @OneToMany(mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Activity> activities = new HashSet<>();

    public void addUsers(User user) {
        this.setUser(user);
        user.getTasks().add(this);
    }

    public void addProjects(Project project) {
        this.setProject(project);
        project.getTasks().add(this);
    }

    public void addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setTask(this);
    }

    public void addWorkLogs(WorkLog workLog) {
        this.workLogs.add(workLog);
        workLog.setTask(this);
    }

    public void addActivities(Activity activity) {
        this.activities.add(activity);
        activity.setTask(this);
    }
}
