package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class AuthFilter implements Filter {

    private static final Pattern ALLOWED_STATIC = Pattern.compile(".+\\.(js|css)$");

    private static final Set<String> ALLOWED_MAPPINGS = Set.of("login", "error/404");

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (allowGuestAccess(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        chain.doFilter(req, res);
    }

    private boolean allowGuestAccess(String uri) {
        return ALLOWED_MAPPINGS.stream().anyMatch(uri::endsWith)
                || ALLOWED_STATIC.matcher(uri).matches();
    }
}