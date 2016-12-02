package filter;

import exception.ExceptionLogger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Egor on 04.11.2016.
 */

public class WebFilter implements Filter {
    private String encoding;

    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("characterEncoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String uri = servletRequest.getRequestURI();
        HttpSession session = servletRequest.getSession(true);
        if (session.getAttribute("username") == null && !uri.contains("signIn") && !uri.contains("registration")) {
            servletResponse.sendRedirect("./signIn");
            return;
        }
        servletRequest.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        encoding = null;
    }
}