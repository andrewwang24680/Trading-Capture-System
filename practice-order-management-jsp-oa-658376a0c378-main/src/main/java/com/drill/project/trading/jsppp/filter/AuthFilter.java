package com.drill.project.trading.jsppp.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebFilter(
        filterName = "AuthFilter",
        urlPatterns = {"*"}
)
@Slf4j
public class AuthFilter implements Filter {
    private Set<String> excludedPaths;

    public void init(FilterConfig config) throws ServletException {
        excludedPaths = new HashSet<>(Arrays.asList("/login", "/register", "/logout"));
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        if (excludedPaths.contains(path) || path.startsWith("/static/images") || httpServletRequest.getSession().getAttribute("user") != null) {
            chain.doFilter(httpServletRequest, response);
        } else {
            log.info("invalid access to {}", path);
            httpServletRequest.getRequestDispatcher("/login").include(request, response);
        }
    }
}