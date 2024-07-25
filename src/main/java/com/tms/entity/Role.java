package com.tms.entity;

public class Role {

    private int roleId;
    private byte accessLevel;

    public Role() {
        this.roleId = 1;
        this.accessLevel = 2;
    }

    public byte getAccessLevel() {
        return accessLevel;
    }
}
