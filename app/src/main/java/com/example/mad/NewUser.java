package com.example.mad;

public class NewUser {
    private String userName, EmaillAddress,password,confirPassword;

    public NewUser() {
    }

    //setters and getters

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmaillAddress(String emaillAddress) {
        EmaillAddress = emaillAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirPassword(String confirPassword) {
        this.confirPassword = confirPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmaillAddress() {
        return EmaillAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirPassword() {
        return confirPassword;
    }
}
