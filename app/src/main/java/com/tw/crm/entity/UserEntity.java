package com.tw.crm.entity;

/**
 * Created by yindezhi on 17/7/8.
 */

public class UserEntity {

    private String username;

    private String password;

    private int role_id;

    private int userStatus;

    public UserEntity(String username, String password, int role_id, int status) {
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.userStatus = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}
