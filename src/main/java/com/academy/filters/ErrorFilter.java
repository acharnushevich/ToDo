package com.academy.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.academy.util.ApplicationConstant.ERROR_PAGE;

@WebFilter(filterName = "ErrorFilter")
public class ErrorFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestUrl = request.getRequestURI();

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Process request failed: " + requestUrl + " - " + e.getMessage());

            response.sendRedirect(request.getContextPath() + ERROR_PAGE + "?url=" + requestUrl);
        }
    }

    @Override
    public void destroy() {
    }
}