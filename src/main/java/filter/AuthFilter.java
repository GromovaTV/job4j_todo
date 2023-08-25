package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("START FILTER");
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do")) {
            System.out.println("1");
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null && !uri.endsWith("reg.do")) {
            System.out.println("2");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
    }
}