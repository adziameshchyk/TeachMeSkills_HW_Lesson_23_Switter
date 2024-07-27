package com.tms.entity;

public class Comment {
    private int commentId;
    private String text;
    private String postId;
    private int userId;

    public Comment(int commentId, String text, int userId) {
        this.commentId = commentId;
        this.text = text;
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

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ", postId='" + postId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
