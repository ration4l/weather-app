package com.ration4l.nl.weather.model;

/**
 * Created by ThanhLongNL on 01-Aug-20.
 */

public class User {

    private String email;
    private String password;

    private String userName;

    private String key;

    public User() {
    }

    public User(String email, String password, String userName, String key) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
