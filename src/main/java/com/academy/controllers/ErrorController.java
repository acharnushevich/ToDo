package com.academy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.academy.util.ApplicationConstant.ERROR_PAGE;
import static com.academy.util.ApplicationConstant.ERROR_VIEW;

@Controller
public class ErrorController {

    @GetMapping(ERROR_PAGE)
    protected ModelAndView loadErrorPage() {
        return new ModelAndView(ERROR_VIEW);
    }
}