package com.tms.dao;

import com.tms.connector.PostgreSQLConnector;
import com.tms.entity.Comment;
import com.tms.entity.Post;
import com.tms.entity.User;
import com.tms.service.CommentService;
import com.tms.service.LikeService;
import com.tms.service.SqlQueryLoader;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostDAO {

    private static final String ADD_POST_SQL_PATH = "sql/post/addPost.sql";
    private static final String GET_POST_BY_ID_SQL_PATH = "sql/post/getPostById.sql";

    public static final String POST_NOT_ADDED_MESSAGE = "Post was not added.";
    public static final String POST_SEARCH_FAILED_MESSAGE = "Post search failed.";

    private static final CommentService commentService = CommentService.getInstance();
    private static final LikeService likeService = LikeService.getInstance();

    public static PostDAO instance;

    private PostDAO() {
    }

    public static PostDAO getInstance() {
        if (instance != null) {
            return instance;
        }
        return new PostDAO();
    }

    public void add(Post post) {
        String addPostQuery = SqlQueryLoader.loadQuery(ADD_POST_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addPostQuery)) {

            preparedStatement.setString(1, post.getText());
            preparedStatement.setInt(2, post.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(POST_NOT_ADDED_MESSAGE);
            e.printStackTrace();
        }

    }

    public Post getPostById(int postId) {
        String getPostByIdQuery = SqlQueryLoader.loadQuery(GET_POST_BY_ID_SQL_PATH);
        Post post = null;

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getPostByIdQuery)) {

            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String text = resultSet.getString("text");
                int userId = resultSet.getInt("user_id");
                List<Comment> comments = commentService.getCommentsByPostId(postId);
                List<User> likes = likeService.getLikesByPostId(postId);
                post = new Post(postId, text, userId, comments, likes);
            }

        } catch (SQLException e) {
            System.out.println(POST_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return post;
    }
}
