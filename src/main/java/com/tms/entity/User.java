package com.tms.entity;

public class User {

    private int userId;
    private final String login;
    private final String password;
    private final String name;
    private final String lastname;
    private final Role role;

    public User(String name, String lastname, String login, String password) {
        this.name = name;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        role = new Role();
    }

    public User(int userId, String name, String lastname, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        role = new Role();
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


    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Role getRole() {
        return role;
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
