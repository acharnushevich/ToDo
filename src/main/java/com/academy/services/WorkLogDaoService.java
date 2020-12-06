package com.academy.services;

import com.academy.comparators.WorkLogsDateComparator;
import com.academy.model.WorkLogDTO;
import com.academy.persistence.entity.WorkLog;
import com.academy.persistence.repositories.TaskRepository;
import com.academy.persistence.repositories.UserRepository;
import com.academy.persistence.repositories.WorkLogRepository;
import com.academy.util.ContentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkLogDaoService {

    private final WorkLogRepository workLogRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ContentUtils contentUtils;

    public void create(WorkLogDTO workLogDTO, Long userId, Long taskId) throws ParseException {
        WorkLog workLog = getWorkLog(workLogDTO);
        workLog.addTasks(taskRepository.findFullById(taskId));
        workLog.addUsers(userRepository.findFullById(userId));
        workLogRepository.save(workLog);
    }

    public void save(WorkLogDTO workLogDTO) throws ParseException {
        WorkLog workLog = workLogRepository.findById(workLogDTO.getId()).orElse(null);
        if (workLog != null) {
            workLog.setTime(Long.valueOf(workLogDTO.getTime()));
            workLog.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(workLogDTO.getDate()));
            workLog.setDescription(workLogDTO.getDescription());
            workLogRepository.save(workLog);
        }
    }

    public void deleteById(Long id) {
        workLogRepository.deleteById(id);
    }

    public List<WorkLogDTO> findAllUserByTaskId(Long id) {
        List<WorkLog> workLogList = workLogRepository.findAllUserByTaskId(id);
        if (!workLogList.isEmpty())
            Collections.sort(workLogList, new WorkLogsDateComparator());
        return getWorkLogDtoList(workLogList);
    }

    private WorkLog getWorkLog(WorkLogDTO workLogDTO) throws ParseException {
        WorkLog workLog = null;
        if (workLogDTO != null) {
            workLog = new WorkLog();
            workLog.setTime(Long.valueOf(workLogDTO.getTime()));
            workLog.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(workLogDTO.getDate()));
            workLog.setDescription(contentUtils.getParam(workLogDTO.getDescription()));
        }
        return workLog;
    }

    private List<WorkLogDTO> getWorkLogDtoList(List<WorkLog> workLogList) {
        List<WorkLogDTO> workLogDTOList = new ArrayList<>();
        for (WorkLog workLog : workLogList) {
            WorkLogDTO workLogDTO = new WorkLogDTO();
            workLogDTO.setId(workLog.getId());
            workLogDTO.setTime(String.valueOf(workLog.getTime()));
            workLogDTO.setDate(new SimpleDateFormat("yyyy-MM-dd").format(workLog.getDate()));
            workLogDTO.setDescription(contentUtils.getParam(workLog.getDescription()));

            if (workLog.getUser() != null) {
                workLogDTO.setUserId(workLog.getUser().getId());
                workLogDTO.setUserName(contentUtils.getParam(workLog.getUser().getName()));
                workLogDTO.setUserSurname(contentUtils.getParam(workLog.getUser().getSurname()));
            }

            workLogDTOList.add(workLogDTO);
        }
        return workLogDTOList;
    }
}