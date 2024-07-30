package com.tms.entity;

import com.tms.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

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

    public Post(int postId, String text, int userId) {
        this.postId = postId;
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
        sb.append("Post id = ").append(postId).append("\n").
                append(lineSeparator).append("\n");

        sb.append(user.getName()).append(" ").append(user.getLastname()).append(":\n").
                append("\n").append(text).append("\n").
        append(lineSeparator).append("\n");

        sb.append("Comments: ").append("\n").
                append(comments.stream()
                        .map(Comment::toString)
                        .collect(Collectors.joining("\n"))
                ).append("\n").
                append(lineSeparator).append("\n");

        sb.append("Likes:\n").append("\n").
                append(likes.stream()
                        .map(u -> u.getName() + " " + u.getLastname())
                        .collect(Collectors.joining("\n"))
                ).append("\n");
        sb.append(lineSeparator);

        return sb.toString();
    }

}
