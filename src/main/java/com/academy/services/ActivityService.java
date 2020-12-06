package com.academy.services;

import com.academy.model.ActivityDTO;
import com.academy.persistence.entity.Activity;
import com.academy.persistence.repositories.ActivityRepository;
import com.academy.util.ContentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ContentUtils contentUtils;

    public List<ActivityDTO> findAllTaskByUserId(Long id) {
        List<Activity> activityList = activityRepository.findAllTaskByUserId(id, PageRequest.of(0, 10));
        return getActivityDtoList(activityList);
    }

    private List<ActivityDTO> getActivityDtoList(List<Activity> activityList) {
        List<ActivityDTO> activityDTOList = new ArrayList<>();
        for (Activity activity : activityList) {
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setId(activity.getId());
            activityDTO.setName(contentUtils.getParam(activity.getName()));
            activityDTO.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(activity.getDate()));

            if (activity.getTask() != null) {
                activityDTO.setTaskName(activity.getTask().getName());
            }
            activityDTOList.add(activityDTO);
        }
        return activityDTOList;
    }
}