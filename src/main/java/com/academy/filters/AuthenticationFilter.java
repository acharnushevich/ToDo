package com.academy.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.academy.util.ApplicationConstant.LOGIN_JSP;
import static com.academy.util.ApplicationConstant.USER;

@WebFilter(filterName = "AuthenticationFilter",
        initParams = {
                @WebInitParam(name = "ALLOWED_PATHS", value = "/login, /registration"),
                @WebInitParam(name = "EXTERNAL_RESOURCES", value = ".css, .js, .png, .ico")})

public class AuthenticationFilter implements Filter {

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

        request.setCharacterEncoding("UTF-8");

        String path = getPath(request);

        String extension = getExtension(request);

        boolean loginUser = session != null && session.getAttribute(USER) != null;
        boolean allowedPath = allowedPathsList.contains(path);
        boolean externalResource = externalResourcesList.contains(extension);

        if (loginUser || allowedPath || externalResource) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return;
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
