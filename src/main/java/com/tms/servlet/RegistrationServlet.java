package com.tms.servlet;

import com.tms.entity.User;
import com.tms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public static final String NAME_HTTP_PARAMETER = "name";
    public static final String LASTNAME_HTTP_PARAMETER = "lastname";
    public static final String LOGIN_HTTP_PARAMETER = "login";
    public static final String PASSWORD_HTTP_PARAMETER = "password";

    public static final String SUCCESSFULLY_ADDED_MESSAGE = "added successfully.";
    public static final String FAILED_ADDITIONAL_MESSAGE = "Failed to add user.";

    UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(NAME_HTTP_PARAMETER);
        String lastname = req.getParameter(LASTNAME_HTTP_PARAMETER);
        String login = req.getParameter(LOGIN_HTTP_PARAMETER);
        String password = req.getParameter(PASSWORD_HTTP_PARAMETER);
        User user = new User(name, lastname, login, password);

        boolean isAdded = userService.add(user);
        if (isAdded) {
            resp.getWriter().println(user.getLogin() + SUCCESSFULLY_ADDED_MESSAGE);
        } else {
            resp.getWriter().println(FAILED_ADDITIONAL_MESSAGE);
        }
    }
}
