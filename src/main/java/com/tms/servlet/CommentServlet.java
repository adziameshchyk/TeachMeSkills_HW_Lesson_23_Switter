package com.tms.servlet;

import com.tms.entity.Comment;
import com.tms.entity.Post;
import com.tms.entity.User;
import com.tms.service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


@WebServlet(urlPatterns = "/comments", name = "CommentServlet")
public class CommentServlet extends HttpServlet {

    public static final String USER_SESSION_ATTRIBUTE = "user";

    public static final String POST_ID_PARAMETER = "post-id";
    public static final String TEXT_PARAMETER = "text";

    private static final CommentService commentService = CommentService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter(TEXT_PARAMETER);
        int postId = Integer.parseInt(req.getParameter(POST_ID_PARAMETER));
        User user = (User) req.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        Comment comment = new Comment(text, postId, user.getUserId());
        commentService.add(comment);
    }

}
