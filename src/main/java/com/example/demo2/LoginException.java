package com.example.demo2;

public class LoginException extends Exception{
    public LoginException(String message){
        super(message);
    }
    public LoginException(String message, Throwable cause){
        super(message, cause);
    }
}
