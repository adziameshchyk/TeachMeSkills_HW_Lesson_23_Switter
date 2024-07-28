package com.tms.servlet;

import com.tms.entity.Post;
import com.tms.entity.User;
import com.tms.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/post", name = "PostServlet")
public class PostServlet extends HttpServlet {

    public static final String USER_SESSION_ATTRIBUTE = "user";

    public static final String POST_ID_PARAMETER = "id";

    public static final String ENCODING_STANDARD_UTF_8 = "UTF-8";
    public static final String TEXT_PLAIN_CONTEXT_TYPE = "text/plain";

    public static final String POST_SEARCH_FAILED_MESSAGE = "Post search failed.";

    private static final PostService postService = PostService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter(POST_ID_PARAMETER);
        resp.setCharacterEncoding(ENCODING_STANDARD_UTF_8);
        resp.setContentType(TEXT_PLAIN_CONTEXT_TYPE);
        if (idParam == null) {
            List<Post> posts = postService.getAllPosts();
            for (Post post : posts) {
                resp.getWriter().println(post);
            }
        } else {
            int postId = Integer.parseInt(idParam);
            Post post = postService.getPostById(postId);
            if (post == null) {
                resp.getWriter().println(POST_SEARCH_FAILED_MESSAGE);
                return;
            }
            resp.getWriter().println(post);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = readTextFromRequest(req);
        User user = (User) req.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        Post post = new Post(text, user.getUserId());
        postService.add(post);
    }

    private String readTextFromRequest(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            req.setCharacterEncoding("UTF-8");
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
