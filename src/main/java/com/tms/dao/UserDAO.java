package com.tms.dao;

import com.tms.connector.PostgreSQLConnector;
import com.tms.entity.User;
import com.tms.service.SqlQueryLoader;

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

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("User was not added.");
            e.printStackTrace();
        }
    }

    public boolean isExistByLogin(String login) {
        String existByLoginQuery = SqlQueryLoader.loadQuery(EXIST_BY_LOGIN_SQL_PATH);
        boolean isExist = false;

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(existByLoginQuery)) {

            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            isExist = resultSet.next();
        } catch (SQLException e) {
            System.out.println("Failed to verify user.");
            e.printStackTrace();
        }
        return isExist;
    }

    public User findById(int userId) {
        String findByIdQuery = SqlQueryLoader.loadQuery(FIND_BY_ID_SQL_PATH);
        User user = null;

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("User search failed.");
            e.printStackTrace();
        }
        return user;
    }

    public User findByLogin(String userLogin) {
        String findByLoginQuery = SqlQueryLoader.loadQuery(FIND_BY_LOGIN_SQL_PATH);
        User user = null;

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByLoginQuery)) {

            preparedStatement.setString(1, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("User search failed.");
            e.printStackTrace();
        }
        return user;
    }

    private static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            String name = resultSet.getString("name");
            String lastname = resultSet.getString("lastname");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            return new User(userId, name, lastname, login, password);
        }
        return null;
    }

    public void updateUser(int userId, User updatedUser) {
        String updateUserQuery = SqlQueryLoader.loadQuery(UPDATE_USER_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {

            preparedStatement.setString(1, updatedUser.getName());
            preparedStatement.setString(2, updatedUser.getLastname());
            preparedStatement.setString(3, updatedUser.getLogin());
            preparedStatement.setString(4, updatedUser.getPassword());
            preparedStatement.setInt(5, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update user.");
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
            System.out.println("Failed to delete user.");
            e.printStackTrace();
        }
    }

}
