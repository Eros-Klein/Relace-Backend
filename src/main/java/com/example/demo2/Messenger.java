package com.example.demo2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo2.Message;
import com.example.demo2.Controller.*;

import java.sql.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Messenger {
    public static boolean sendMessage(String sender, String receiver, String message, String password){
        try{
            Connection connection = SQLLogin.connect();
            if(Login.isAccountValid(sender, password)){
                System.out.println(new java.sql.Date(new Date().getTime()));
                System.out.println(new java.sql.Date(new Date().getTime()));
                System.out.println(new Date().getTime());
                System.out.println(sender + " " + receiver + " " + message);
                String statement = "INSERT INTO MESSAGES (SENDER, RECEIVER, MESSAGE, DATE_SENT) VALUES ('" + sender + "', '" + receiver + "', '" + message + "', TO_DATE('" + (new Date().getYear() + 1900) + "-" + (new Date().getMonth() + 1) + "-" + (new Date().getDay() + 5) + " " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds() + "', '" + "YYYY-MM-DD HH24:MI:SS" + "'))";
                if(parametersAreValid(sender, receiver, message)){
                    connection.createStatement().execute(statement);
                }
                connection.close();
                return true;

            }
            connection.close();
            return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Message[] getMessages(String username, String password){
        try{
            Connection connection = SQLLogin.connect();
            String statement = "SELECT * FROM MESSAGES WHERE RECEIVER = '" + username + "'";
            ResultSet rawResults = connection.createStatement().executeQuery(statement);
            List<Message> results = new java.util.ArrayList<>();
            while(rawResults.next()){
                results.add(new Message(rawResults.getString(1), rawResults.getString(2), rawResults.getString(3), rawResults.getString(4)));
            }
            connection.close();

            return results.toArray(new Message[0]);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return new Message[0];
        }
    }
    private static boolean parametersAreValid(String sender, String receiver, String message) throws SQLException {
        if(sender != null && receiver != null && message != null){
            if(sender.length() <= 20 && receiver.length() <= 20 && message.length() <= 300){
                Connection connection = SQLLogin.connect();
                String statement = "SELECT * FROM LOGIN WHERE USERNAME = '" + sender + "'";
                String statement2 = "SELECT * FROM LOGIN WHERE USERNAME = '" + receiver + "'";
                ResultSet rawResults = connection.createStatement().executeQuery(statement);
                ResultSet rawResults2 = connection.createStatement().executeQuery(statement2);
                if(rawResults.next() && rawResults2.next()){
                    connection.close();
                    return true;
                }
                else{
                    connection.close();
                }
            }
        }
        return false;
    }
}
