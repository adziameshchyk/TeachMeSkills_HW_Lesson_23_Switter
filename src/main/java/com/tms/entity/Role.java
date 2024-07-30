package com.tms.entity;

public class Role {

    public static final int ADMIN_USER_ACCESS_LEVEL = 1;
    public static final int SIMPLE_USER_ACCESS_LEVEL = 0;

    private int roleId;
    private byte accessLevel;

    public Role() {
        this.accessLevel = SIMPLE_USER_ACCESS_LEVEL;
    }

    public byte getAccessLevel() {
        return accessLevel;
    }
}
