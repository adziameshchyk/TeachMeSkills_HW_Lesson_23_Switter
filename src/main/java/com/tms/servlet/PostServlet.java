package com.tms.servlet;

import com.tms.entity.Post;
import com.tms.entity.Role;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/posts", name = "PostServlet")
public class PostServlet extends HttpServlet {

    public static final String USER_SESSION_ATTRIBUTE = "user";

    public static final String POST_ID_PARAMETER = "post-id";
    public static final String TEXT_PARAMETER = "text";

    public static final String ENCODING_STANDARD_UTF_8 = "UTF-8";
    public static final String TEXT_PLAIN_CONTEXT_TYPE = "text/plain";

    public static final String POST_SEARCH_FAILED_MESSAGE = "Post search failed.";
    public static final String IMPOSSIBLE_CHANGE_POST_OF_ANOTHER_USER_MESSAGE = "It is impossible to change another user's post.";

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = readTextFromRequest(req);
        Map<String, String> paramMap = parseParametersFromReqBody(reqBody);

        int postId = Integer.parseInt(paramMap.get(POST_ID_PARAMETER));
        String text = paramMap.get(TEXT_PARAMETER);
        User user = (User) req.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        Post post = postService.getPostById(postId);
        if (user.getUserId() == post.getUserId() || user.getRole().getAccessLevel() > Role.ADMIN_USER_ACCESS_LEVEL) {
            Post updatedPost = new Post(postId, text, user.getUserId());
            postService.updatePost(updatedPost);
        } else {
            resp.getWriter().println(IMPOSSIBLE_CHANGE_POST_OF_ANOTHER_USER_MESSAGE);
        }
    }

    private String readTextFromRequest(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            req.setCharacterEncoding(ENCODING_STANDARD_UTF_8);
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

    private Map<String, String> parseParametersFromReqBody(String reqBody) {
        Map<String, String> paramMap = new HashMap<>();
        String[] params = reqBody.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                paramMap.put(keyValue[0], keyValue[1]);
            }
        }
        return paramMap;
    }
}
