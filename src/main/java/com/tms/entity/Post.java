package com.tms.entity;

import com.tms.service.UserService;

import java.util.List;

public class Post {

    private int postId;
    private String text;
    private int userId;
    private List<Comment> comments;
    private List<User> likes;

    public Post(String text, int userId) {
        this.text = text;
        this.userId = userId;
    }

    public Post(int postId, String text, int userId, List<Comment> comments, List<User> likes) {
        this.postId = postId;
        this.text = text;
        this.userId = userId;
        this.comments = comments;
        this.likes = likes;
    }

    public int getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        UserService userService = UserService.getInstance();
        User user = userService.findById(userId);

        StringBuilder sb = new StringBuilder();
        String lineSeparator = "----------------------------------------";

        sb.append(lineSeparator).append("\n");
        sb.append("User: ").append(user.getName()).append(" ").append(user.getLastname()).append("\n");
        sb.append(lineSeparator).append("\n");

        sb.append("Post:\n").append(text);

        sb.append(lineSeparator).append("\n");
        sb.append("Comments: ").append(comments).append("\n");
        sb.append("Likes: ").append(likes).append("\n");
        sb.append(lineSeparator);

        return sb.toString();
    }

}
