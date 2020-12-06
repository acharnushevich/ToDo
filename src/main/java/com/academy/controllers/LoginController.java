package com.academy.controllers;

import com.academy.model.UserDTO;
import com.academy.services.UserService;
import com.academy.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import static com.academy.util.ApplicationConstant.*;
import static com.academy.util.ErrorConstant.*;

@RequiredArgsConstructor
@Controller
@SessionAttributes(value = {USER, ERROR})
public class LoginController {

    private final UserService userService;
    private final LoginUtils loginUtils;

    @GetMapping(LOGIN_PAGE)
    protected ModelAndView loadLoginPage() {
        return new ModelAndView(LOGIN_VIEW);
    }

    @PostMapping(LOGIN_PAGE)
    public ModelAndView processLogin(@Valid @ModelAttribute UserDTO userDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(LOGIN_VIEW);
            return modelAndView;
        }

        String viewName;
        try {
            userDTO = Optional.ofNullable(userService.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword())).orElseThrow(() -> new RuntimeException(INVALID_USERS_DATA));

            if (!userDTO.getProfileEnable()) {
                throw new RuntimeException(USER_LOCKED);
            } else {
                modelAndView.addObject(USER, loginUtils.getSessionUserDTO(userDTO));
            }
            viewName = REDIRECT_VIEW + TASKS_PAGE;
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
            viewName = LOGIN_VIEW;
        }
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @PostMapping(REGISTRATION_PAGE)
    public ModelAndView processRegistration(@Valid @ModelAttribute UserDTO userDTO, BindingResult br) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.setViewName(LOGIN_VIEW);
            return modelAndView;
        }

        String viewName;
        try {
            if (userService.existsByEmail(userDTO.getEmail())) {
                throw new RuntimeException(USER_ALREADY_EXIST);
            }

            userDTO = userService.create(userDTO);
            modelAndView.addObject(USER, loginUtils.getSessionUserDTO(userDTO));
            viewName = REDIRECT_VIEW + TASKS_PAGE;
        } catch (Exception e) {
            modelAndView.addObject(ERROR, e.getMessage());
            viewName = LOGIN_VIEW;
        }
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping(LOGOUT_PAGE)
    public ModelAndView processLogOut(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return new ModelAndView(REDIRECT_VIEW + LOGIN_PAGE);
    }
}
