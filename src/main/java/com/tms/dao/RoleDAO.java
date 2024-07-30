package com.tms.dao;

import com.tms.connector.PostgreSQLConnector;
import com.tms.service.SqlQueryLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleDAO {

    private static final String SET_SIMPLE_USER_ROLE_SQL_PATH = "sql/role/setSimpleRole.sql";

    private static final int FIRST_PARAMETER_OF_SQL_QUERY = 1;

    public static final String ROLE_NOT_ADDED_MESSAGE = "Role was not added.";

    public static RoleDAO instance;

    private RoleDAO() {
    }

    public static RoleDAO getInstance() {
        if (instance != null) {
            return instance;
        }
        return new RoleDAO();
    }

    public void setSimpleRoleByLogin(int userId) {
        String setSimpleUserRoleQuery = SqlQueryLoader.loadQuery(SET_SIMPLE_USER_ROLE_SQL_PATH);

        try (Connection connection = PostgreSQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(setSimpleUserRoleQuery)) {

            preparedStatement.setInt(FIRST_PARAMETER_OF_SQL_QUERY, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ROLE_NOT_ADDED_MESSAGE);
            e.printStackTrace();
        }
    }
}
