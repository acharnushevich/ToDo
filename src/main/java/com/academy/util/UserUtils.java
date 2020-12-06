package com.academy.util;

import com.academy.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserUtils {

    private final ContentUtils contentUtils;

    public SessionUserDTO getSessionUserDTO(UserDTO userDTO) {
        SessionUserDTO sessionUserDTO = null;
        if (userDTO != null) {
            sessionUserDTO = new SessionUserDTO();
            sessionUserDTO.setSessionUserId(userDTO.getId());
            sessionUserDTO.setSessionUserEmail(contentUtils.getParam(userDTO.getEmail()));
            sessionUserDTO.setSessionUserPassword(contentUtils.getParam(userDTO.getPassword()));
            sessionUserDTO.setSessionUserName(contentUtils.getParam(userDTO.getName()));
            sessionUserDTO.setSessionUserSurname(contentUtils.getParam(userDTO.getSurname()));
            sessionUserDTO.setSessionUserDescription(contentUtils.getParam(userDTO.getDescription()));
            sessionUserDTO.setSessionUserProjectsId(contentUtils.getParam(userDTO.getProjectsId()));
            sessionUserDTO.setSessionUserRole(userDTO.getRole());
            sessionUserDTO.setSessionUserProfileEnable(userDTO.getProfileEnable());
        }
        return sessionUserDTO;
    }
}
