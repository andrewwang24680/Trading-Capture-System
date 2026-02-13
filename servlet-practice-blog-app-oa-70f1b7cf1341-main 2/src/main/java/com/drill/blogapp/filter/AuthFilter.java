package com.drill.blogapp.filter;

import com.drill.blogapp.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/home"})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
        log.info("AuthFilter initialized");
    }
    @Override
    public void destroy() {
        log.info("AuthFilter destroyed");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User currentUser = null;
        if (session != null) {
            currentUser = (User) session.getAttribute("user");
        }
        if (currentUser != null) {
            log.info("User '{}' is authenticated, allowing access to {}",
                    currentUser.getUsername(), req.getRequestURI());
            chain.doFilter(request, response);
        } else {
            String requestedUrl = req.getRequestURI();
            String queryString = req.getQueryString();

            String targetUrl = requestedUrl;
            if (queryString != null) {
                targetUrl += "?" + queryString;
            }
            log.warn("Unauthorized access attempt to {}. Redirecting to login page.", targetUrl);
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("targetUrl", targetUrl);
            resp.sendRedirect(req.getContextPath() + "/login.html");
        }
    }
}