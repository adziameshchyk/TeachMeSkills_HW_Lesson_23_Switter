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

    private static final int FIRST_PARAMETER_OF_SQL_QUERY = 1;
    private static final int SECOND_PARAMETER_OF_SQL_QUERY = 2;
    private static final int THIRD_PARAMETER_OF_SQL_QUERY = 3;

    public static final String COMMENT_COLUMN_LABEL = "comment_id";
    public static final String TEXT_COLUMN_LABEL = "text";
    public static final String USER_ID_COLUMN_LABEL = "user_id";

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

            preparedStatement.setString(FIRST_PARAMETER_OF_SQL_QUERY, comment.getText());
            preparedStatement.setInt(SECOND_PARAMETER_OF_SQL_QUERY, comment.getUserId());
            preparedStatement.setInt(THIRD_PARAMETER_OF_SQL_QUERY, comment.getPostId());
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

            preparedStatement.setInt(FIRST_PARAMETER_OF_SQL_QUERY, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int commentId = resultSet.getInt(COMMENT_COLUMN_LABEL);
                String text = resultSet.getString(TEXT_COLUMN_LABEL);
                int userId = resultSet.getInt(USER_ID_COLUMN_LABEL);
                comments.add(new Comment(commentId, text, postId, userId));
            }
        } catch (SQLException e) {
            System.out.println(COMMENTS_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return comments;
    }
}
