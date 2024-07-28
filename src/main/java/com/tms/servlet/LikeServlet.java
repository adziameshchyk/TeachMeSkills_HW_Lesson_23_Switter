package com.tms.servlet;

import com.tms.entity.Comment;
import com.tms.entity.User;
import com.tms.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/like", name = "LikeServlet")
public class LikeServlet extends HttpServlet {

    public static final String USER_SESSION_ATTRIBUTE = "user";

    public static final String POST_ID_PARAMETER = "post-id";

    public static final LikeService likeService = LikeService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter(POST_ID_PARAMETER));
        User user = (User) req.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        likeService.add(postId, user.getUserId());
    }
}
