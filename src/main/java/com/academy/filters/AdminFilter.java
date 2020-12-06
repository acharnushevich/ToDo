package com.academy.filters;

import com.academy.model.SessionUserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.academy.util.ApplicationConstant.*;
import static com.academy.util.ErrorConstant.ADMIN_ACCESS_ERROR;

@WebFilter(filterName = "AdminFilter",
        initParams = {
                @WebInitParam(name = "ALLOWED_PATHS", value = "/admin/"),
                @WebInitParam(name = "EXTERNAL_RESOURCES", value = ".css, .js, .png, .ico")})

public class AdminFilter implements Filter {

    private List<String> allowedPathsList;
    private List<String> externalResourcesList;

    @Override
    public void init(FilterConfig filterConfig) {
        allowedPathsList = Arrays.asList(filterConfig.getInitParameter("ALLOWED_PATHS").split(", "));
        externalResourcesList = Arrays.asList(filterConfig.getInitParameter("EXTERNAL_RESOURCES").split(", "));

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String path = getPath(request);

        String extension = getExtension(request);

        boolean loginUser = session != null && session.getAttribute(USER) != null;
        boolean loginAdmin = false;
        boolean allowedPath = false;
        boolean externalResource = externalResourcesList.contains(extension);

        for (String item : allowedPathsList) {
            allowedPath = path.startsWith(item);
            if (allowedPath) break;
        }

        if (loginUser) {
            SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute(USER);
            loginAdmin = sessionUserDTO.getSessionUserRole().toString().equals(ROLE_ADMIN);
        }

        if (allowedPath) {
            if (loginAdmin || externalResource) {
                filterChain.doFilter(request, response);
            } else {
                request.getSession().setAttribute(ERROR, ADMIN_ACCESS_ERROR);
                response.sendRedirect(request.getContextPath() + TASKS_PAGE);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    private String getExtension(HttpServletRequest request) {
        String extension = "";
        int index = request.getRequestURI().lastIndexOf(".");
        if (index >= 0) {
            extension = request.getRequestURI().substring(index);
        }
        return removeUriJsessionid(extension);
    }

    private String removeUriJsessionid(String uri) {
        if (uri != null && uri.toLowerCase().contains(";jsessionid")) {
            uri = uri.substring(0, uri.indexOf(";"));
        }
        return uri;
    }

    private String getPath(HttpServletRequest request) {
        return removeUriJsessionid(request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", ""));
    }
}
