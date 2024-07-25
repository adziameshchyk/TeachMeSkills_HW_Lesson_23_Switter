package com.tms.service;

import com.tms.dao.UserDAO;
import com.tms.entity.User;

public class UserService {

    private static final UserDAO userDAO = new UserDAO();

    public boolean add(User user) {
        if (isExistByLogin(user.getLogin())) {
            System.out.println("User with this login already exists.");
            return false;
        }

        userDAO.add(user);
        System.out.println("User added successfully.");
        return true;
    }

    public boolean isExistByLogin(String login) {
        return userDAO.isExistByLogin(login);
    }

    public User findById(int userId) {
        return userDAO.findById(userId);
    }

    public void updateUser(int userId, User updatedUser) {
        userDAO.updateUser(userId, updatedUser);
    }

    public void deleteUserById(int userId) {
        userDAO.deleteUserById(userId);
    }
}
