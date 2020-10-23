package com.wangyuming.netty.nio.msgpack;

import org.msgpack.annotation.Message;

@Message
public class UserInfo {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String toString(){
        return "User{id=" + this.id + ", username: " + this.username + ", password: " + this.password + "}";
    }
}
