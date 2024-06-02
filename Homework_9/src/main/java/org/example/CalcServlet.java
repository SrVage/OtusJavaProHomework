package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class CalcServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(CalcServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));
        int c = operation(a, b);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().printf("<html><body>");
        resp.getWriter().printf(Integer.toString(c));
        resp.getWriter().printf("</body></html>");
        resp.getWriter().close();
    }

    protected abstract int operation(int a, int b);

    @Override
    public void init() throws ServletException {
        logger.debug(getClass().getSimpleName() + " servlet initialized");
    }
}