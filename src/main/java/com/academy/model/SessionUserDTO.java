package com.academy.model;

import com.academy.persistence.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionUserDTO {
    private Long sessionUserId;
    private String sessionUserEmail;
    private String sessionUserPassword;
    private String sessionUserName;
    private String sessionUserSurname;
    private String sessionUserDescription;
    private String sessionUserProjectsId;
    private Roles sessionUserRole;
    private Boolean sessionUserProfileEnable;
}
