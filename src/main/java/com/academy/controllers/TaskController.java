package com.academy.controllers;

import com.academy.model.*;
import com.academy.persistence.entity.enums.TaskStatus;
import com.academy.services.AttachmentService;
import com.academy.services.ProjectService;
import com.academy.services.TaskService;
import com.academy.services.WorkLogDaoService;
import com.academy.util.TaskUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.academy.util.ApplicationConstant.*;

@RequiredArgsConstructor
@Controller
@SessionAttributes(value = {USER, ERROR})
@RequestMapping({"/", TASKS_PAGE})
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final AttachmentService attachmentService;
    private final WorkLogDaoService workLogDaoService;
    private final TaskUtils taskUtils;

    @GetMapping
    protected ModelAndView loadTasksPage(@ModelAttribute(USER) SessionUserDTO sessionUserDTO) {
        ModelAndView modelAndView = new ModelAndView();

        List<TaskStatusDTO> taskStatusDTO;
        List<PriorityDTO> priorityDTO;
        List<ProjectDTO> projectList;
        List<TaskDTO> taskList;

        try {
            taskStatusDTO = taskUtils.getDictionaryStatus();
            priorityDTO = taskUtils.getDictionaryPriorities();
            projectList = projectService.findAllByProjectsId(sessionUserDTO.getSessionUserProjectsId());
            taskList = taskService.findAllUsersAndProjectsById(sessionUserDTO.getSessionUserProjectsId());

            modelAndView.addObject(TASKS_STATUS, taskStatusDTO);
            modelAndView.addObject(PRIORITIES, priorityDTO);
            modelAndView.addObject(PROJECTS, projectList);
            modelAndView.addObject(TASKS, taskList);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(TASKS_VIEW);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView processTasksPage(@ModelAttribute(USER) SessionUserDTO sessionUserDTO, @Valid @ModelAttribute TaskDTO taskDTO, BindingResult br, @RequestParam(FILE) MultipartFile[] files) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(TASKS_VIEW);
            return modelAndView;
        }

        try {
            taskService.create(taskDTO, files, TaskStatus.Create.toString(), sessionUserDTO.getSessionUserId());
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + TASKS_PAGE);
        return modelAndView;
    }

    @GetMapping("/{taskId}")
    public ModelAndView loadTaskPage(@PathVariable("taskId") Long taskId) {
        ModelAndView modelAndView = new ModelAndView(TASK_VIEW);

        TaskDTO taskDTO;
        List<PriorityDTO> priorityDTO;
        List<AttachmentDTO> attachmentDTOList;
        List<WorkLogDTO> workLogDTOList;
        try {
            taskDTO = taskService.findUsersAndProjectsById(taskId);
            priorityDTO = taskUtils.getDictionaryPriorities();
            attachmentDTOList = attachmentService.findAllByTaskId(taskId);
            workLogDTOList = workLogDaoService.findAllUserByTaskId(taskId);

            modelAndView.addObject(WORK_LOGS, workLogDTOList);
            modelAndView.addObject(PRIORITIES, priorityDTO);
            modelAndView.addObject(ATTACHMENTS, attachmentDTOList);
            modelAndView.addObject(TASK, taskDTO);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/{taskId}")
    public ModelAndView processTaskPage(@PathVariable("taskId") Long taskId, @ModelAttribute(USER) SessionUserDTO sessionUserDTO, @ModelAttribute TaskDTO taskDTO, @RequestParam(FILE) MultipartFile[] files) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            taskService.save(taskDTO, files, taskUtils.getUserDto(sessionUserDTO));
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }

        if (taskDTO.getTaskStatus().equals(TaskStatus.Open)) {
            modelAndView.setViewName(REDIRECT_VIEW + TASKS_PAGE + "/" + taskId);
        } else {
            modelAndView.setViewName(REDIRECT_VIEW + TASKS_PAGE);
        }
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView processSearchTask(@ModelAttribute(USER) SessionUserDTO sessionUserDTO, @ModelAttribute SearchTaskDTO searchTaskDTO) {
        ModelAndView modelAndView = new ModelAndView(TASKS_VIEW);

        List<TaskStatusDTO> taskStatusDTO;
        List<PriorityDTO> priorityDTO;
        List<ProjectDTO> projectDTOList;
        List<TaskDTO> taskDTOList;

        try {
            taskStatusDTO = taskUtils.getDictionaryStatus();
            priorityDTO = taskUtils.getDictionaryPriorities();
            projectDTOList = projectService.findAllByProjectsId(sessionUserDTO.getSessionUserProjectsId());
            taskDTOList = taskService.findAllSearchUserAndProject(searchTaskDTO, sessionUserDTO.getSessionUserProjectsId());

            modelAndView.addObject(TASKS_STATUS, taskStatusDTO);
            modelAndView.addObject(PRIORITIES, priorityDTO);
            modelAndView.addObject(PROJECTS, projectDTOList);
            modelAndView.addObject(TASKS, taskDTOList);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        } finally {
            modelAndView.addObject(SEARCH_FILTER, "true");
        }
        return modelAndView;
    }

    @PostMapping("/{taskId}/add-worklog")
    public ModelAndView processWorkLog(@PathVariable("taskId") Long taskId, @ModelAttribute(USER) SessionUserDTO sessionUserDTO, @Valid @ModelAttribute WorkLogDTO workLogDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(TASK_VIEW);
            return modelAndView;
        }

        try {
            workLogDaoService.create(workLogDTO, sessionUserDTO.getSessionUserId(), taskId);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + TASKS_PAGE + "/" + taskId);
        return modelAndView;
    }

    @PostMapping("/{taskId}/edit-worklog")
    public ModelAndView processEditWorkLog(@PathVariable("taskId") Long taskId, @Valid @ModelAttribute WorkLogDTO workLogDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(TASK_VIEW);
            return modelAndView;
        }

        try {
            workLogDaoService.save(workLogDTO);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + TASKS_PAGE + "/" + taskId);
        return modelAndView;
    }

    @PostMapping("/{taskId}/delete-worklog")
    public ModelAndView processDeleteWorkLog(@PathVariable("taskId") Long taskId, @RequestParam(WORK_LOG_ID) Long workLogId) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_VIEW + TASKS_PAGE + "/" + taskId);
        try {
            workLogDaoService.deleteById(workLogId);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/{taskId}/download-file")
    public ResponseEntity processDownloadFile(@PathVariable("taskId") Long taskId, @RequestParam(TASK_ATTACHMENT_ID) Long attachmentId) throws IOException, SQLException {
        ResponseEntity responseEntity = attachmentService.downloadFile(attachmentId);
        return responseEntity;
    }

    @PostMapping("/{taskId}/delete-file")
    public ModelAndView processDeleteFile(@PathVariable("taskId") Long taskId, @RequestParam(TASK_ATTACHMENT_ID) Long attachmentId) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_VIEW + TASKS_PAGE + "/" + taskId);
        try {
            attachmentService.deleteById(attachmentId);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }
}
