package com.academy.services;

import com.academy.comparators.TasksDateComparator;
import com.academy.model.*;
import com.academy.persistence.entity.*;
import com.academy.persistence.entity.enums.TaskStatus;
import com.academy.persistence.repositories.ProjectRepository;
import com.academy.persistence.repositories.TaskRepository;
import com.academy.persistence.repositories.UserRepository;
import com.academy.persistence.specification.TaskSpecification;
import com.academy.util.ContentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.academy.util.ErrorConstant.INVALID_TASK_DATA;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ContentUtils contentUtils;

    public void create(TaskDTO taskDTO, MultipartFile[] files, String activityName, Long userId) throws ParseException, SQLException, IOException {
        Task task = new Task();
        User user = userRepository.findFullById(userId);
        Project project = projectRepository.findFullById(Long.valueOf(taskDTO.getProjectId()));

        Activity activity = new Activity();
        activity.setName(activityName);
        activity.setDate(new Date());
        activity.addUsers(user);
        task.addActivities(activity);

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] fileData = file.getBytes();
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileData(new SerialBlob(fileData));
            task.addAttachments(attachment);
        }

        task.setName(taskDTO.getName());
        task.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(taskDTO.getDate()));
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setTaskStatus(TaskStatus.Open);
        task.addUsers(user);
        task.addProjects(project);
        taskRepository.save(task);
    }

    public void save(TaskDTO taskDTO, MultipartFile[] files, UserDTO userDTO) throws ParseException, IOException, SQLException {
        Task task = taskRepository.findFullById(taskDTO.getId());
        if (task != null) {
            User user = userRepository.findFullById(userDTO.getId());
            String activityName = taskDTO.getTaskStatus().toString();
            if (TaskStatus.Open.equals(task.getTaskStatus()) && TaskStatus.Open.equals(taskDTO.getTaskStatus())) {
                activityName = TaskStatus.Edit.toString();
            } else if (!TaskStatus.Open.equals(task.getTaskStatus()) && TaskStatus.Open.equals(taskDTO.getTaskStatus())) {
                activityName = TaskStatus.Open.toString();
            }

            Activity activity = new Activity();
            activity.setName(activityName);
            activity.setDate(new Date());
            activity.addUsers(user);
            task.addActivities(activity);

            task.setName(taskDTO.getName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(taskDTO.getDate()));
            task.setDescription(taskDTO.getDescription());
            task.setPriority(taskDTO.getPriority());
            task.setTaskStatus(taskDTO.getTaskStatus());

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                byte[] fileData = file.getBytes();
                Attachment attachment = new Attachment();
                attachment.setFileName(file.getOriginalFilename());
                attachment.setFileData(new SerialBlob(fileData));
                task.addAttachments(attachment);
            }
            taskRepository.save(task);
        } else {
            throw new RuntimeException(INVALID_TASK_DATA);
        }
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskDTO findUsersAndProjectsById(Long id) {
        Task task = taskRepository.findUsersAndProjectsById(id);
        return getTaskDto(task);
    }

    public List<TaskDTO> findAllUsersAndProjectsById(String projectsId) {
        List<Task> taskList = new ArrayList<>();
        if (!projectsId.equals("")) {
            for (String projectId : projectsId.split(",")) {
                List<Task> itemList = taskRepository.findAllUsersAndProjectsById(Long.valueOf(projectId));
                if (itemList != null && !itemList.isEmpty())
                    taskList.addAll(itemList);
            }
            if (!taskList.isEmpty())
                Collections.sort(taskList, new TasksDateComparator());
        }
        return getTaskDtoList(taskList);
    }

    public List<TaskDTO> findAllSearchUserAndProject(SearchTaskDTO searchTaskDTO, String userProjectsId) {
        List<Task> taskList = new ArrayList<>();
        if (!userProjectsId.equals("")) {
            TaskSpecification taskSpecification = new TaskSpecification();
            taskList = taskRepository.findAll(taskSpecification.findAllSearchSpecification(searchTaskDTO, userProjectsId));
            if (!taskList.isEmpty())
                Collections.sort(taskList, new TasksDateComparator());
        }
        return getTaskDtoList(taskList);
    }

    private Attachment getAttachment(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();
        attachment.setFileName(contentUtils.getParam(attachmentDTO.getFileName()));
        attachment.setFileData(attachmentDTO.getFileData());
        return attachment;
    }

    private TaskDTO getTaskDto(Task task) {
        TaskDTO taskDTO = null;
        if (task != null) {
            taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setName(contentUtils.getParam(task.getName()));
            taskDTO.setDate(new SimpleDateFormat("yyyy-MM-dd").format(task.getDate()));
            taskDTO.setDescription(contentUtils.getParam(task.getDescription()));
            taskDTO.setPriority(task.getPriority());
            taskDTO.setTaskStatus(task.getTaskStatus());

            if (task.getUser() != null) {
                taskDTO.setUserName(task.getUser().getName());
            }

            if (task.getProject() != null) {
                taskDTO.setProjectName(task.getProject().getName());
            }
        }
        return taskDTO;
    }

    private List<TaskDTO> getTaskDtoList(List<Task> taskList) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : taskList) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setName(contentUtils.getParam(task.getName()));
            taskDTO.setDate(new SimpleDateFormat("yyyy-MM-dd").format(task.getDate()));
            taskDTO.setDescription(contentUtils.getParam(task.getDescription()));
            taskDTO.setPriority(task.getPriority());
            taskDTO.setTaskStatus(task.getTaskStatus());

            if (task.getUser() != null) {
                taskDTO.setUserName(contentUtils.getParam(task.getUser().getName()));
            }

            if (task.getProject() != null) {
                taskDTO.setProjectName(contentUtils.getParam(task.getProject().getName()));
            }

            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }
}
