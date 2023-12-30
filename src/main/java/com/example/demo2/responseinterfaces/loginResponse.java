package com.example.demo2.responseinterfaces;

public class loginResponse {
    public String token;
    public boolean success;

    public loginResponse(String token, boolean success){
        this.token = token;
        this.success = success;
    }
    public loginResponse(boolean success){
        this.success = success;
    }
}
