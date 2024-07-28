package com.tms.dao;

import com.tms.connector.PostgreSQLConnector;
import com.tms.entity.User;
import com.tms.service.CommentService;
import com.tms.service.SqlQueryLoader;
import com.tms.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikeDAO {

    private static final String GET_LIKES_BY_POST_ID_SQL_PATH = "sql/like/getLikesByPostId.sql";
    private static final String ADD_LIKE_SQL_PATH = "sql/like/addLike.sql";

    public static final String LIKES_SEARCH_FAILED_MESSAGE = "Likes search failed.";
    public static final String LIKE_NOT_ADDED_MESSAGE = "Like was not added.";

    private static final UserService userService = UserService.getInstance();

    public static LikeDAO instance;

    private LikeDAO() {
    }

    public static LikeDAO getInstance() {
        if (instance != null) {
            return instance;
        }
        return new LikeDAO();
    }

    public void add(int postId, int userId) {
        String addLikeByPostIdQuery = SqlQueryLoader.loadQuery(ADD_LIKE_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addLikeByPostIdQuery)) {

            preparedStatement.setInt(1, postId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(LIKE_NOT_ADDED_MESSAGE);
            e.printStackTrace();
        }
    }

    public List<User> getLikesByPostId(int postId) {
        String getLikeByPostIdQuery = SqlQueryLoader.loadQuery(GET_LIKES_BY_POST_ID_SQL_PATH);
        List<User> likes = new ArrayList<>();

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getLikeByPostIdQuery)) {

            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int likeId = resultSet.getInt("like_id");
                int userId = resultSet.getInt("user_id");
                likes.add(userService.findById(userId));
            }
        } catch (SQLException e) {
            System.out.println(LIKES_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return likes;
    }
}
