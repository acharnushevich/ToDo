package com.academy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTaskDTO {
    String fromDate;
    String toDate;
    String name;
    String priority;
    String taskStatus;
    String projectId;
}
