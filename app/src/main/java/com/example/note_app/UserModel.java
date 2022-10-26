package com.example.note_app;

import androidx.annotation.NonNull;

public class UserModel {

    private String u_address;
    private String u_password;
    private String u_username;

    public UserModel(String u_address, String u_password, String u_username) {
        this.u_address = u_address;
        this.u_password = u_password;
        this.u_username = u_username;
    }

    public UserModel() {}

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserModel{" +
                "u_address='" + u_address + '\'' +
                ", u_password='" + u_password + '\'' +
                ", u_username='" + u_username + '\'' +
                '}';
    }
}
