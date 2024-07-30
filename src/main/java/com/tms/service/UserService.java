package com.tms.service;

import com.tms.dao.UserDAO;
import com.tms.entity.User;
import com.tms.exception.UserAlreadyExistsException;

public class UserService {

    public static final String USER_ALREADY_EXISTS_MESSAGE = "User with this login already exists.";
    public static final String USER_ADDED_SUCCESSFULLY_EXISTS_MESSAGE = "User added successfully.";

    private static final UserDAO userDAO = UserDAO.getInstance();
    private static final RoleService roleService = RoleService.getInstance();
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance != null) {
            return instance;
        }
        return new UserService();
    }

    public boolean add(User user) throws UserAlreadyExistsException {
        if (isExistByLogin(user.getLogin())) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE);
        }

        userDAO.add(user);
        int userId = findByLogin(user.getLogin()).getUserId();
        roleService.setSimpleRoleById(userId);
        System.out.println(USER_ADDED_SUCCESSFULLY_EXISTS_MESSAGE);
        return true;
    }

    public boolean isExistByLogin(String login) {
        return userDAO.isExistByLogin(login);
    }

    public User findById(int userId) {
        return userDAO.findById(userId);
    }

    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    public void updateUser(int userId, User updatedUser) {
        userDAO.updateUser(userId, updatedUser);
    }

    public void deleteUserById(int userId) {
        userDAO.deleteUserById(userId);
    }

}
