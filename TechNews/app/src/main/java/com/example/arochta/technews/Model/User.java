package com.example.arochta.technews.Model;

import java.io.Serializable;

/**
 * Created by arochta on 17/07/2017.
 */

public class User implements Serializable{

    int userID;
    private String email;
    private String name;
    private String password;

    public User(){
        this.userID = 1;
        this.email = "tal@tal.com";
        this.name = "tal a";
        this.password = "tal";
    }

    public User(String email, String password){
        this.email  = email;
        this.password = password;
    }

    public User(String name,String email, String password){
        this.name = name;
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
