package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Egor on 04.11.2016.
 */

public class CharsetFilter implements Filter {
    private String encoding;
    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("characterEncoding");
        context = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        context.log("Charset was set: " + encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        encoding = null;
        context = null;
    }
}