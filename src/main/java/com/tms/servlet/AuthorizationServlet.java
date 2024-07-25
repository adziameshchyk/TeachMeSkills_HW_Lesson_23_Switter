package com.tms.servlet;

import com.tms.entity.User;
import com.tms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {

    public static final String LOGIN_HTTP_PARAMETER = "login";
    public static final String PASSWORD_HTTP_PARAMETER = "password";
    public static final String USER_SESSION_ATTRIBUTE = "user";

    public static final String WRONG_PASSWORD_MESSAGE = "Wrong password, try again.";

    UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN_HTTP_PARAMETER);
        String password = req.getParameter(PASSWORD_HTTP_PARAMETER);
        User user = userService.findByLogin(login);
        if (user.getPassword().equals(password)) {
            req.getSession().setAttribute(USER_SESSION_ATTRIBUTE, user);
        } else {
            resp.getWriter().println(WRONG_PASSWORD_MESSAGE);
        }
    }
}
