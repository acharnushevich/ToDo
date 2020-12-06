package com.academy.controllers;

import com.academy.model.*;
import com.academy.services.ProjectService;
import com.academy.services.UserService;
import com.academy.util.AdminUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.academy.util.ApplicationConstant.*;
import static com.academy.util.ErrorConstant.*;
import static com.academy.util.ErrorConstant.USER_ALREADY_EXIST;

@RequiredArgsConstructor
@Controller
@SessionAttributes(value = {USER, ERROR})
@RequestMapping(ADMIN_PAGE)
public class AdminController {

    private final UserService userService;
    private final ProjectService projectService;
    private final AdminUtils adminUtils;

    @GetMapping(ADMIN_USERS_PAGE)
    public ModelAndView loadAdminUsersPage(@ModelAttribute(USER) SessionUserDTO sessionUserDTO) {
        ModelAndView modelAndView = new ModelAndView(ADMIN_USERS_VIEW);

        List<RoleDTO> roleDTO;
        List<ProjectDTO> projectDTOList;
        List<UserDTO> userDTOList;

        try {
            roleDTO = adminUtils.getDictionaryRoles();
            projectDTOList = projectService.findAll();
            userDTOList = userService.findAllWithOutId(sessionUserDTO.getSessionUserId());

            modelAndView.addObject(ROLES, roleDTO);
            modelAndView.addObject(PROJECTS, projectDTOList);
            modelAndView.addObject(USERS, userDTOList);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(ADMIN_USERS_PAGE)
    public ModelAndView processAdminNewUsers(@Valid @ModelAttribute UserDTO userDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(ADMIN_USERS_VIEW);
            return modelAndView;
        }

        try {
            if (userService.existsByEmail(userDTO.getEmail())) {
                throw new RuntimeException(USER_ALREADY_EXIST);
            }

            userService.create(userDTO);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + ADMIN_PAGE + ADMIN_USERS_PAGE);
        return modelAndView;
    }

    @PostMapping(ADMIN_USERS_PAGE + "/{userId}")
    public ModelAndView processAdminEditUsers(@PathVariable("userId") Long userId, @Valid @ModelAttribute UserDTO userDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(ADMIN_USERS_VIEW);
            return modelAndView;
        }

        try {
            if (userService.existsByIdNotAndEmail(userDTO.getId(), userDTO.getEmail())) {
                throw new RuntimeException(USER_ALREADY_EXIST);
            }

            userService.adminSave(userDTO);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + ADMIN_PAGE + ADMIN_USERS_PAGE);
        return modelAndView;
    }

    @PostMapping(ADMIN_USERS_PAGE + "/{userId}/delete")
    public ModelAndView processAdminDeleteUsers(@PathVariable("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_VIEW + ADMIN_PAGE + ADMIN_USERS_PAGE);
        try {
            userService.deleteById(userId);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(ADMIN_USERS_PAGE + "/search")
    public ModelAndView processAdminSearchUsers(@ModelAttribute(USER) SessionUserDTO sessionUserDTO, @ModelAttribute SearchUserDTO searchUserDTO) {
        ModelAndView modelAndView = new ModelAndView(ADMIN_USERS_VIEW);

        List<RoleDTO> roleDTO;
        List<ProjectDTO> projectDTOList;
        List<UserDTO> userDTOList;

        try {
            roleDTO = adminUtils.getDictionaryRoles();
            projectDTOList = projectService.findAll();
            userDTOList = userService.findAllSearchIdNot(sessionUserDTO.getSessionUserId(), searchUserDTO);

            modelAndView.addObject(ROLES, roleDTO);
            modelAndView.addObject(PROJECTS, projectDTOList);
            modelAndView.addObject(USERS, userDTOList);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        } finally {
            modelAndView.addObject(SEARCH_FILTER, "true");
        }
        return modelAndView;
    }

    @GetMapping(ADMIN_PROJECTS_PAGE)
    public ModelAndView loadAdminProjectsPage() {
        ModelAndView modelAndView = new ModelAndView(ADMIN_PROJECTS_VIEW);

        List<ProjectDTO> projectDTOList;

        try {
            projectDTOList = projectService.findAll();

            modelAndView.addObject(PROJECTS, projectDTOList);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(ADMIN_PROJECTS_PAGE)
    public ModelAndView processAdminNewProjects(@Valid @ModelAttribute ProjectDTO projectDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(ADMIN_PROJECTS_VIEW);
            return modelAndView;
        }

        try {
            if (projectService.existsByName(projectDTO.getName())) {
                throw new RuntimeException(PROJECT_ALREADY_EXIST);
            }

            projectService.create(projectDTO);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + ADMIN_PAGE + ADMIN_PROJECTS_PAGE);
        return modelAndView;
    }

    @PostMapping(ADMIN_PROJECTS_PAGE + "/{projectId}")
    public ModelAndView processAdminEditProjects(@PathVariable("projectId") Long projectId, @Valid @ModelAttribute ProjectDTO projectDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(ADMIN_PROJECTS_VIEW);
            return modelAndView;
        }

        try {
            if (projectService.existsByIdNotAndName(projectDTO.getId(), projectDTO.getName())) {
                throw new RuntimeException(PROJECT_ALREADY_EXIST);
            }

            projectService.save(projectDTO);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + ADMIN_PAGE + ADMIN_PROJECTS_PAGE);
        return modelAndView;
    }

    @PostMapping(ADMIN_PROJECTS_PAGE + "/{projectId}/delete")
    public ModelAndView processAdminDeleteProjects(@PathVariable("projectId") Long projectId) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_VIEW + ADMIN_PAGE + ADMIN_PROJECTS_PAGE);
        try {
            projectService.deleteById(projectId);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(ADMIN_PROJECTS_PAGE + "/search")
    public ModelAndView processAdminSearchProjects(@ModelAttribute SearchProjectDTO searchProjectDTO) {
        ModelAndView modelAndView = new ModelAndView(ADMIN_PROJECTS_VIEW);

        List<ProjectDTO> projectDTOList;

        try {
            projectDTOList = projectService.findAllSearch(searchProjectDTO);

            modelAndView.addObject(PROJECTS, projectDTOList);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        } finally {
            modelAndView.addObject(SEARCH_FILTER, "true");
        }
        return modelAndView;
    }
}
