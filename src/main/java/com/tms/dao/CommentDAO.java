package com.tms.dao;

import com.tms.connector.PostgreSQLConnector;
import com.tms.entity.Comment;
import com.tms.service.SqlQueryLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    private static final String GET_COMMENTS_BY_POST_ID_SQL_PATH = "sql/comment/getCommentsByPostId.sql";
    private static final String ADD_COMMENT_SQL_PATH = "sql/comment/addComment.sql";

    public static final String COMMENT_NOT_ADDED_MESSAGE = "Post was not added.";
    public static final String COMMENTS_SEARCH_FAILED_MESSAGE = "Comments search failed.";

    public static CommentDAO instance;

    private CommentDAO() {
    }

    public static CommentDAO getInstance() {
        if (instance != null) {
            return instance;
        }
        return new CommentDAO();
    }

    public void add(Comment comment) {
        String addCommentQuery = SqlQueryLoader.loadQuery(ADD_COMMENT_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addCommentQuery)) {

            preparedStatement.setString(1, comment.getText());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setInt(3, comment.getPostId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(COMMENT_NOT_ADDED_MESSAGE);
            e.printStackTrace();
        }
    }

    public List<Comment> getCommentsByPostId(int postId) {
        String getCommentsByPostIdQuery = SqlQueryLoader.loadQuery(GET_COMMENTS_BY_POST_ID_SQL_PATH);
        List<Comment> comments = new ArrayList<>();

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getCommentsByPostIdQuery)) {

            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int commentId = resultSet.getInt("comment_id");
                String text = resultSet.getString("text");
                int userId = resultSet.getInt("user_id");
                comments.add(new Comment(commentId, text, postId, userId));
            }
        } catch (SQLException e) {
            System.out.println(COMMENTS_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return comments;
    }
}
