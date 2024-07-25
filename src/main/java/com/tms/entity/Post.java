package com.tms.entity;

import java.util.List;

public class Post {

    private int postId;
    private String text;
    private User user;
    private List<Comment> comments;
    private List<String> likes;

    public int getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<String> getLikes() {
        return likes;
    }

}
