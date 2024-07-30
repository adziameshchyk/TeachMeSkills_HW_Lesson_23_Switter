package com.tms.service;

import com.tms.dao.PostDAO;
import com.tms.entity.Post;

import java.util.List;

public class PostService {

    private static final PostDAO postDAO = PostDAO.getInstance();
    private static PostService instance;

    private PostService() {
    }

    public static PostService getInstance() {
        if (instance != null) {
            return instance;
        }
        return new PostService();
    }


    public void add(Post post) {
        postDAO.add(post);
    }

    public Post getPostById(int postId) {
        return postDAO.getPostById(postId);
    }

    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }

    public void updatePost(Post updatedPost) {
        postDAO.updatePost(updatedPost);
    }
}
