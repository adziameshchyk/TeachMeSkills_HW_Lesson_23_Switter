package com.tms.dao;

import com.tms.connector.PostgreSQLConnector;
import com.tms.entity.User;
import com.tms.service.RoleService;
import com.tms.service.SqlQueryLoader;
import com.tms.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String ADD_USER_SQL_PATH = "sql/user/addUser.sql";
    private static final String EXIST_BY_LOGIN_SQL_PATH = "sql/user/existByLogin.sql";
    private static final String FIND_BY_ID_SQL_PATH = "sql/user/findById.sql";
    private static final String FIND_BY_LOGIN_SQL_PATH = "sql/user/findByLogin.sql";
    private static final String UPDATE_USER_SQL_PATH = "sql/user/updateUser.sql";
    private static final String DELETE_USER_SQL_PATH = "sql/user/deleteUser.sql";

    private static final int FIRST_PARAMETER_OF_SQL_QUERY = 1;
    private static final int SECOND_PARAMETER_OF_SQL_QUERY = 2;
    private static final int THIRD_PARAMETER_OF_SQL_QUERY = 3;
    private static final int FOURTH_PARAMETER_OF_SQL_QUERY = 4;
    private static final int FIFTH_PARAMETER_OF_SQL_QUERY = 5;

    public static final String USER_ID_COLUMN_LABEL = "user_id";
    public static final String NAME_COLUMN_LABEL = "name";
    public static final String LASTNAME_COLUMN_LABEL = "lastname";
    public static final String LOGIN_COLUMN_LABEL = "login";
    public static final String PASSWORD_COLUMN_LABEL = "password";

    public static final String USER_NOT_ADDED_MESSAGE =  "User was not added.";
    public static final String FAILED_VERIFY_USER_MESSAGE =  "Failed to verify user.";
    public static final String USER_SEARCH_FAILED_MESSAGE =  "User search failed.";
    public static final String USER_UPDATE_FAILED_MESSAGE =  "Failed to update user.";
    public static final String USER_DELETE_FAILED_MESSAGE =  "Failed to delete user.";

    private static final UserService userService = UserService.getInstance();
    private static UserDAO instance;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (instance != null) {
            return instance;
        }
        return new UserDAO();
    }

    public void add(User user) {
        String addUserQuery = SqlQueryLoader.loadQuery(ADD_USER_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery)) {

            preparedStatement.setString(FIRST_PARAMETER_OF_SQL_QUERY, user.getName());
            preparedStatement.setString(SECOND_PARAMETER_OF_SQL_QUERY, user.getLastname());
            preparedStatement.setString(THIRD_PARAMETER_OF_SQL_QUERY, user.getLogin());
            preparedStatement.setString(FOURTH_PARAMETER_OF_SQL_QUERY, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(USER_NOT_ADDED_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean isExistByLogin(String login) {
        String existByLoginQuery = SqlQueryLoader.loadQuery(EXIST_BY_LOGIN_SQL_PATH);
        boolean isExist = false;

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(existByLoginQuery)) {

            preparedStatement.setString(FIRST_PARAMETER_OF_SQL_QUERY, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            isExist = resultSet.next();
        } catch (SQLException e) {
            System.out.println(FAILED_VERIFY_USER_MESSAGE);
            e.printStackTrace();
        }
        return isExist;
    }

    public User findById(int userId) {
        String findByIdQuery = SqlQueryLoader.loadQuery(FIND_BY_ID_SQL_PATH);
        User user = null;

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery)) {

            preparedStatement.setInt(FIRST_PARAMETER_OF_SQL_QUERY, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println(USER_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return user;
    }

    public User findByLogin(String userLogin) {
        String findByLoginQuery = SqlQueryLoader.loadQuery(FIND_BY_LOGIN_SQL_PATH);
        User user = null;

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByLoginQuery)) {

            preparedStatement.setString(FIRST_PARAMETER_OF_SQL_QUERY, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println(USER_SEARCH_FAILED_MESSAGE);
            e.printStackTrace();
        }
        return user;
    }

    private static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            int userId = resultSet.getInt(USER_ID_COLUMN_LABEL);
            String name = resultSet.getString(NAME_COLUMN_LABEL);
            String lastname = resultSet.getString(LASTNAME_COLUMN_LABEL);
            String login = resultSet.getString(LOGIN_COLUMN_LABEL);
            String password = resultSet.getString(PASSWORD_COLUMN_LABEL);
            return new User(userId, name, lastname, login, password);
        }
        return null;
    }

    public void updateUser(int userId, User updatedUser) {
        String updateUserQuery = SqlQueryLoader.loadQuery(UPDATE_USER_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {

            preparedStatement.setString(FIRST_PARAMETER_OF_SQL_QUERY, updatedUser.getName());
            preparedStatement.setString(SECOND_PARAMETER_OF_SQL_QUERY, updatedUser.getLastname());
            preparedStatement.setString(THIRD_PARAMETER_OF_SQL_QUERY, updatedUser.getLogin());
            preparedStatement.setString(FOURTH_PARAMETER_OF_SQL_QUERY, updatedUser.getPassword());
            preparedStatement.setInt(FIFTH_PARAMETER_OF_SQL_QUERY, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(USER_UPDATE_FAILED_MESSAGE);
            e.printStackTrace();
        }
    }

    public void deleteUserById(int userId) {
        String deleteUserQuery = SqlQueryLoader.loadQuery(DELETE_USER_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(USER_DELETE_FAILED_MESSAGE);
            e.printStackTrace();
        }
    }

}
