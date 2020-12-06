package com.academy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import static com.academy.util.ErrorConstant.INVALID_PROJECT_NAME;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    @NotBlank(message = INVALID_PROJECT_NAME)
    private String name;
    private String description;
}
