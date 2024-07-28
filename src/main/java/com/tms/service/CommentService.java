package com.tms.service;

import com.tms.dao.CommentDAO;
import com.tms.dao.PostDAO;
import com.tms.entity.Comment;

import java.util.List;

public class CommentService {

    private static final CommentDAO commentDAO = CommentDAO.getInstance();
    private static CommentService instance;

    private CommentService() {
    }

    public static CommentService getInstance() {
        if (instance != null) {
            return instance;
        }
        return new CommentService();
    }

    public void add(Comment comment) {
        commentDAO.add(comment);
    }

    public List<Comment> getCommentsByPostId(int postId) {
        return commentDAO.getCommentsByPostId(postId);
    }
}
