package com.tms.service;

import com.tms.dao.RoleDAO;

public class RoleService {

    private static final RoleDAO roleDAO = RoleDAO.getInstance();
    private static RoleService instance;

    private RoleService() {
    }

    public static RoleService getInstance() {
        if (instance != null) {
            return instance;
        }
        return new RoleService();
    }

    public void setSimpleRoleById(int userId) {
        roleDAO.setSimpleRoleByLogin(userId);
    }

//    public void setAdminRoleByLogin(String login) {
//        roleDAO.setSimpleRoleByLogin(login);
//    }

}
