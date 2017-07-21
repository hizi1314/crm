package com.tw.crm.entity;

/**
 * Created by yindezhi on 17/7/8.
 */

public class UserEntity {



    private String userid ;

    private String username;

    private String password;

    private String role_id;

    private int userStatus;

    public  UserEntity(){

    }
    public  UserEntity(String userid,String username ){
        this.userid=userid ;
        this.username=username;
    }

    public UserEntity(String username, String password, String role_id, int status,String userid) {
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.userStatus = status;
        this.userid=userid ;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
