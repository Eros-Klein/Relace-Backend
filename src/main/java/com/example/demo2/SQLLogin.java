package com.example.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum SQLLogin {
        /*
        String dataBaseURL = "jdbc:oracle:thin:@student.cloud.htl-leonding.ac.at:31521/ora19db";
        String username = "if210052";
        String password = "oracle";
        */
    DATABASEURL("jdbc:oracle:thin:@student.cloud.htl-leonding.ac.at:31521/ora19db"),
    USERNAME("if210052"),
    PASSWORD("oracle");
    private String value;
    SQLLogin(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
    static Connection connect(){
        try {
            return DriverManager.getConnection(DATABASEURL.getValue(), USERNAME.getValue(), PASSWORD.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
