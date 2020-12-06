package com.academy.model;

import com.academy.persistence.entity.enums.Priority;
import com.academy.persistence.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import static com.academy.util.ErrorConstant.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    @NotBlank(message = INVALID_TASK_NAME)
    private String name;
    @NotBlank(message = INVALID_TASK_DATE)
    private String date;
    private String description;
    private Priority priority;
    private TaskStatus taskStatus;
    @NotBlank(message = INVALID_TASK_PROJECT_NAME)
    private String projectId;
    private String projectName;
    private String userName;
}
