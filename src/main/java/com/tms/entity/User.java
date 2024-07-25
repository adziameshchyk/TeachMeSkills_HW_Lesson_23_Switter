package com.tms.entity;

public class User {

    private int userId;
    private String login;
    private String password;
    private String name;
    private String lastname;
    private Role role;

    public User(String name, String lastname, String login, String password) {
        this.name = name;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }


    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                '}';
    }
}
