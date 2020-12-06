package com.academy.model;

import com.academy.persistence.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import static com.academy.util.ErrorConstant.INVALID_USER_EMAIL;
import static com.academy.util.ErrorConstant.INVALID_USER_PASSWORD;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = INVALID_USER_EMAIL)
    private String email;
    @NotBlank(message = INVALID_USER_PASSWORD)
    private String password;
    private String name;
    private String surname;
    private String description;
    private String projectsId;
    private Roles role;
    private Boolean profileEnable;
}
