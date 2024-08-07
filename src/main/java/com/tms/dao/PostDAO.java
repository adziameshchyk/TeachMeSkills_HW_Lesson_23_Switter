package com.tms.dao;

import com.tms.connector.PostgreSQLConnector;
import com.tms.entity.Comment;
import com.tms.entity.Post;
import com.tms.entity.User;
import com.tms.service.CommentService;
import com.tms.service.LikeService;
import com.tms.service.SqlQueryLoader;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    private static final String ADD_POST_SQL_PATH = "sql/post/addPost.sql";
    private static final String GET_POST_BY_ID_SQL_PATH = "sql/post/getPostById.sql";
    private static final String GET_ALL_POSTS_SQL_PATH = "sql/post/getAllPosts.sql";
    private static final String UPDATE_POST_SQL_PATH = "sql/post/updatePost.sql";

    private static final int FIRST_PARAMETER_OF_SQL_QUERY = 1;
    private static final int SECOND_PARAMETER_OF_SQL_QUERY = 2;

    public static final String POST_COLUMN_LABEL = "post_id";
    public static final String TEXT_COLUMN_LABEL = "text";
    public static final String USER_ID_COLUMN_LABEL = "user_id";

    public static final String POST_NOT_ADDED_MESSAGE = "Post was not added.";
    public static final String POST_NOT_UPDATE_MESSAGE = "Post was not update.";
    public static final String POST_SEARCH_FAILED_MESSAGE = "Post search failed.";
    public static final String POSTS_SEARCH_FAILED_MESSAGE = "Posts search failed.";

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

            preparedStatement.setString(FIRST_PARAMETER_OF_SQL_QUERY, post.getText());
            preparedStatement.setInt(SECOND_PARAMETER_OF_SQL_QUERY, post.getUserId());
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

            preparedStatement.setInt(FIRST_PARAMETER_OF_SQL_QUERY, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String text = resultSet.getString(TEXT_COLUMN_LABEL);
                int userId = resultSet.getInt(USER_ID_COLUMN_LABEL);
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

    public List<Post> getAllPosts() {
        String getAllPostsQuery = SqlQueryLoader.loadQuery(GET_ALL_POSTS_SQL_PATH);
        List<Post> posts = new ArrayList<>();

        try (Connection connection = PostgreSQLConnector.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(getAllPostsQuery);
            while (resultSet.next()) {
                int postId = resultSet.getInt(POST_COLUMN_LABEL);
                String text = resultSet.getString(TEXT_COLUMN_LABEL);
                int userId = resultSet.getInt(USER_ID_COLUMN_LABEL);
                List<Comment> comments = commentService.getCommentsByPostId(postId);
                List<User> likes = likeService.getLikesByPostId(postId);
                posts.add(new Post(postId, text, userId, comments, likes));
            }

        } catch (SQLException e) {
            System.out.println(POSTS_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return posts;
    }

    public void updatePost(Post updatedPost) {
        String updatePostQuery = SqlQueryLoader.loadQuery(UPDATE_POST_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updatePostQuery)) {

            preparedStatement.setString(FIRST_PARAMETER_OF_SQL_QUERY, updatedPost.getText());
            preparedStatement.setInt(SECOND_PARAMETER_OF_SQL_QUERY, updatedPost.getPostId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(POST_NOT_UPDATE_MESSAGE);
            e.printStackTrace();
        }
    }
}
