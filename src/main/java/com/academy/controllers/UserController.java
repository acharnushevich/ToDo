package com.academy.controllers;

import com.academy.model.ActivityDTO;
import com.academy.model.ProjectDTO;
import com.academy.model.SessionUserDTO;
import com.academy.model.UserDTO;
import com.academy.services.ActivityService;
import com.academy.services.ProjectService;
import com.academy.services.UserService;
import com.academy.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.academy.util.ApplicationConstant.*;
import static com.academy.util.ErrorConstant.USER_ALREADY_EXIST;

@RequiredArgsConstructor
@Controller
@SessionAttributes(value = {USER, ERROR})
@RequestMapping(PROFILE_PAGE)
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final ActivityService activityService;
    private final UserUtils userUtils;

    @GetMapping
    public ModelAndView loadProfilePage(@ModelAttribute(USER) SessionUserDTO sessionUserDTO) {
        ModelAndView modelAndView = new ModelAndView(PROFILE_VIEW);

        List<ProjectDTO> projectDTOList;
        List<ActivityDTO> activityDTOList;

        try {
            projectDTOList = projectService.findAllByProjectsId(sessionUserDTO.getSessionUserProjectsId());
            activityDTOList = activityService.findAllTaskByUserId(sessionUserDTO.getSessionUserId());

            modelAndView.addObject(PROJECTS, projectDTOList);
            modelAndView.addObject(ACTIVITIES, activityDTOList);
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView processProfile(@Valid @ModelAttribute UserDTO editUserDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(PROFILE_VIEW);
            return modelAndView;
        }

        try {
            if (userService.existsByIdNotAndEmail(editUserDTO.getId(), editUserDTO.getEmail())) {
                throw new RuntimeException(USER_ALREADY_EXIST);
            }

            modelAndView.addObject(USER, userUtils.getSessionUserDTO(userService.save(editUserDTO)));
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
        }
        modelAndView.setViewName(REDIRECT_VIEW + PROFILE_PAGE);
        return modelAndView;
    }
}
