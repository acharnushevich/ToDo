package com.academy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import static com.academy.util.ErrorConstant.INVALID_WORKLOG_DATE;
import static com.academy.util.ErrorConstant.INVALID_WORKLOG_TIME;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkLogDTO {
    private Long id;
    @NotBlank(message = INVALID_WORKLOG_TIME)
    private String time;
    @NotBlank(message = INVALID_WORKLOG_DATE)
    private String date;
    private String description;
    private Long userId;
    private String userName;
    private String userSurname;
}
