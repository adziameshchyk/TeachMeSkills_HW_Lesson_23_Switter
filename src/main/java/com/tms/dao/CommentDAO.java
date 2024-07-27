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

    public List<Comment> getCommentsByPostId(int postId) {
        String getCommentByPostIdQuery = SqlQueryLoader.loadQuery(GET_COMMENTS_BY_POST_ID_SQL_PATH);
        List<Comment> comments = new ArrayList<>();

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getCommentByPostIdQuery)) {

            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int commentId = resultSet.getInt("comment_id");
                String text = resultSet.getString("text");
                int userId = resultSet.getInt("user_id");
                comments.add(new Comment(commentId, text, userId));
            }
        } catch (SQLException e) {
            System.out.println(COMMENTS_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return comments;
    }
}
