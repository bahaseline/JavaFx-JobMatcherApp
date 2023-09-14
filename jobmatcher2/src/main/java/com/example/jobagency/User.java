package com.example.jobagency;

public class User {
    //set up the variables
    private String username;
    private String password;
    private String email;

    //set up the constructor
    public User(String username, String password, String s) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //setters and getters
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
