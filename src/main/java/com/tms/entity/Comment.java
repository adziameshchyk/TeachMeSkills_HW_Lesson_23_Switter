package com.tms.entity;

import com.tms.service.UserService;

public class Comment {
    private int commentId;
    private String text;
    private int postId;
    private int userId;

    public Comment(String text, int postId, int userId) {
        this.text = text;
        this.postId = postId;
        this.userId = userId;
    }

    public Comment(int commentId, String text, int postId, int userId) {
        this.commentId = commentId;
        this.text = text;
        this.postId = postId;
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }

    public int getPostId() {
        return postId;
    }

    @Override
    public String toString() {
        UserService userService = UserService.getInstance();
        User user = userService.findById(userId);

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(user.getName()).append(" ").append(user.getLastname()).append(":\n");
        sb.append(text);

        return sb.toString();
    }
}
