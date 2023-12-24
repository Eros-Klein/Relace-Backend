package com.example.demo2;

import lombok.extern.java.Log;

import java.sql.*;
/*
    * This class is used to handle all login related tasks
    * This includes adding, removing, and checking if an account is valid
    * This class is a singleton
    * This class is not meant to be instantiated from outside
 */
public final class Login {
    private static Login instance;
    private Login(){}
    private static Login getInstance(){
        if(instance == null){
            instance = new Login();
        }
        return instance;
    }
    public static boolean addAccount(String username, String password, String email){
        try{
            username = username.trim();
            password = password.trim();
            email = email.trim();

            if (!getInstance().isEmailValid(email)){
                throw new LoginException("Email is not valid");
            }
            else if(!getInstance().isUsernameValid(username)){
                throw new LoginException("Username is not valid");
            }
            else if(!getInstance().isPasswordValid(password)){
                throw new LoginException("Password is not valid");
            }
            else if(!isAccountValid(username, password)){
                Connection connection = SQLLogin.connect();
                String statement = "INSERT INTO LOGIN VALUES ('" + email + "', '" + username + "', '" + PasswordEncryptor.encrypt(password) + "')";
                Statement stmt = connection.createStatement();
                stmt.executeQuery(statement);
                connection.close();
                return true;
            }
            Logger.getInstance().logWarning("Account already exists", "Login", "addAccount");
            return false;
        }
        catch (SQLException | LoginException e){
            Logger.getInstance().logError(e.getMessage(), "Login", "addAccount");
            return false;
        }
    }
    public static boolean removeAccount(String username, String password){
        try{
            Connection connection = SQLLogin.connect();

            String statement = "DELETE FROM LOGIN WHERE USERNAME = '" + username + "' AND PASS = '" + PasswordEncryptor.encrypt(password) + "'";
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(statement);
            if(!results.next()){
                Logger.getInstance().logWarning("Account does not exist", "Login", "removeAccount");
                connection.close();
                return false;
            }
            Logger.getInstance().logInfo("Account " + username + " removed", "Login", "removeAccount");
            connection.close();
            return true;
        }
        catch (SQLException e){
            Logger.getInstance().logError(e.getMessage(), "Login", "removeAccount");
            return false;
        }
    }
    public static boolean isAccountValid(String username, String password){
        try{
            Connection connection = SQLLogin.connect();

            String statement = "SELECT * FROM LOGIN WHERE USERNAME = '" + username + "' AND PASS = '" + PasswordEncryptor.encrypt(password) + "'";
            Statement stmt = connection.createStatement();
            ResultSet rawResults = stmt.executeQuery(statement);
            boolean result = rawResults.next();
            Logger.getInstance().logInfo("Information for " + username + " was matching database: " + result, "Login", "isAccountValid");
            connection.close();
            return result;
        }
        catch (SQLException e){
            Logger.getInstance().logError(e.getMessage(), "Login", "isAccountValid");
            return false;
        }
    }
    private boolean isUsernameValid(String username){
        return username.length() >= 3 && username.length() <= 20;
    }
    private boolean isPasswordValid(String password){
        return password.length() >= 8 && password.length() <= 50;
    }
    private boolean isEmailValid(String email){
        return email.split("@").length == 2 && email.split("@")[1].split("\\.").length >= 2 && email.length() <= 100;
    }
}
