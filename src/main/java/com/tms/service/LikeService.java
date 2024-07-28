package com.tms.service;

import com.tms.dao.LikeDAO;
import com.tms.entity.User;

import java.util.List;

public class LikeService {

    private static final LikeDAO likeDAO = LikeDAO.getInstance();
    private static LikeService instance;

    private LikeService() {
    }

    public static LikeService getInstance() {
        if (instance != null) {
            return instance;
        }
        return new LikeService();
    }

    public void add(int postId, int userId) {
        likeDAO.add(postId, userId);
    }

    public List<User> getLikesByPostId(int likeId) {
        return likeDAO.getLikesByPostId(likeId);
    }

}
