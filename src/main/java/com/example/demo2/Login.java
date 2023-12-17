package com.example.demo2;

import java.sql.*;

public class Login {
    public static boolean addAccount(String username, String password, String email){
        try{
            Connection connection = SQLLogin.connect();

            String statement = "INSERT INTO LOGIN VALUES ('" + email + "', '" + username + "', '" + password + "')";
            Statement stmt = connection.createStatement();
            stmt.executeQuery(statement);
            connection.close();
            return true;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean removeAccount(String username, String password){
        try{
            Connection connection = SQLLogin.connect();

            String statement = "DELETE FROM LOGIN WHERE USERNAME = '" + username + "' AND PASS = '" + password + "'";
            Statement stmt = connection.createStatement();
            stmt.executeQuery(statement);
            connection.close();
            return true;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean isAccountValid(String username, String password){
        try{
            Connection connection = SQLLogin.connect();

            String statement = "SELECT * FROM LOGIN WHERE USERNAME = '" + username + "' AND PASS = '" + password + "'";
            Statement stmt = connection.createStatement();
            ResultSet rawResults = stmt.executeQuery(statement);
            boolean result = rawResults.next();
            connection.close();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
