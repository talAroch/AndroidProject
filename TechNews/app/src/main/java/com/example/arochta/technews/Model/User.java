package com.example.arochta.technews.Model;

/**
 * Created by arochta on 17/07/2017.
 */

public class User {

    int userID;
    private String email;
    private String name;
    private String password;

    public User(){}

    public User(String email, String password){
        this.email  = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}