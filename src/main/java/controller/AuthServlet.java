package controller;

import model.User;
import persistence.HbnStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("START AUTH SERVLET");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = HbnStore.instOf().findUserByEmail(req.getParameter("email"));
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("USER != NULL");
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            System.out.println("USER == NULL");
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}