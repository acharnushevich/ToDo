package com.academy.util;

import com.academy.model.*;
import com.academy.persistence.entity.enums.Priority;
import com.academy.persistence.entity.enums.Roles;
import com.academy.persistence.entity.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TaskUtils {

    private final ContentUtils contentUtils;

    public List<PriorityDTO> getDictionaryPriorities() {
        PriorityDTO priorityItem;
        List<PriorityDTO> priorityList = new ArrayList<>();

        for (Priority itemDao : Priority.values()) {
            priorityItem = new PriorityDTO();
            priorityItem.setId(itemDao.name());
            priorityItem.setName(itemDao.name());
            priorityList.add(priorityItem);
        }
        return priorityList;
    }

    public List<TaskStatusDTO> getDictionaryStatus() {
        TaskStatusDTO statusItem;
        List<TaskStatusDTO> statusList = new ArrayList<>();

        for (TaskStatus itemDao : TaskStatus.values()) {
            statusItem = new TaskStatusDTO();
            statusItem.setId(itemDao.name());
            statusItem.setName(itemDao.name());
            statusList.add(statusItem);
        }
        return statusList;
    }

    public UserDTO getUserDto(SessionUserDTO sessionUserDTO) {
        UserDTO userDTO = null;
        if (sessionUserDTO != null) {
            userDTO = new UserDTO();
            userDTO.setId(sessionUserDTO.getSessionUserId());
            userDTO.setEmail(contentUtils.getParam(sessionUserDTO.getSessionUserEmail()));
            userDTO.setPassword(contentUtils.getParam(sessionUserDTO.getSessionUserPassword()));
            userDTO.setName(contentUtils.getParam(sessionUserDTO.getSessionUserName()));
            userDTO.setSurname(contentUtils.getParam(sessionUserDTO.getSessionUserSurname()));
            userDTO.setDescription(contentUtils.getParam(sessionUserDTO.getSessionUserDescription()));
            userDTO.setProjectsId(contentUtils.getParam(sessionUserDTO.getSessionUserProjectsId()));
            userDTO.setRole(sessionUserDTO.getSessionUserRole());
            userDTO.setProfileEnable(sessionUserDTO.getSessionUserProfileEnable());
        }
        return userDTO;
    }
}
