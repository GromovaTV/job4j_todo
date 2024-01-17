package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.IndexService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(IndexServlet.class.getName());
    private IndexService service = new IndexService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Start Get");
        service.handleGet(req, resp);
        LOG.info("Finish Get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Start Post");
        service.handlePost(req, resp);
        LOG.info("Finish Post");
    }
}
