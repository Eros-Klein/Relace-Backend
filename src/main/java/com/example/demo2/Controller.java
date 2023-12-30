package com.example.demo2;

import com.example.demo2.responseinterfaces.loginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;
import com.example.demo2.Message;
import com.example.demo2.Controller.*;
import com.example.demo2.SQLLogin.*;
import com.example.demo2.Messenger.*;
import com.example.demo2.Login.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping("/hello")
    public String[] hellos(){
        String[] results = {"Hello", "World"};
        return results;
    }
    @GetMapping("/insertUser/{username}/{password}/{email}")
    public String insertAccount(@PathVariable String email ,@PathVariable String username, @PathVariable String password){
        return toJson(Login.addAccount(username, password, email));
    }
    @DeleteMapping("/removeAccount/{username}/{password}")
    public boolean removeAccount(@PathVariable String username, @PathVariable String password){
        return Login.removeAccount(username, password);
    }
    @GetMapping("/validUser/{username}/{password}")
    public String validAccount(@PathVariable String username, @PathVariable String password){
        return toJson(Login.isAccountValid(username, password));
    }
    @GetMapping("/getNewToken/{username}/{password}")
    public String login(@PathVariable String username, @PathVariable String password){
        return Login.login(username, password);
    }
    @GetMapping("/login/{username}/{token}")
    public boolean loginByToken(@PathVariable String username, @PathVariable String token){
            return Login.loginByToken(username, token);
    }
    @PostMapping("/sendMessage/{sender}/{receiver}/{message}/{password}")
    public boolean sendMessage(@PathVariable String sender, @PathVariable String receiver, @PathVariable String message, @PathVariable String password){
        return Messenger.sendMessage(sender, receiver, message, password);
    }
    @GetMapping("/getMessages/{username}/{password}")
    public Message[] getMessages(@PathVariable String username, @PathVariable String password){
        return Messenger.getMessages(username, password);
    }

    public static String toJson(Object obj){
        ObjectMapper Obj = new ObjectMapper();
        try{
            return Obj.writeValueAsString(obj);
        } catch (Exception e) {
            Logger.getInstance().logError(e.getMessage(), "Controller", "toJson");
            throw new RuntimeException(e);
        }
    }









    //Testing Purposes
    @GetMapping("/getUsers")
    public String[] getUsers(){
            try{
                Connection connection = SQLLogin.connect();

                String statement = "SELECT * FROM LOGIN";
                Statement stmt = connection.createStatement();
                ResultSet rawResults = stmt.executeQuery(statement);
                List<String> results = new ArrayList<>();
                while(rawResults.next()){
                    results.add(rawResults.getString(1));
                }
                connection.close();
                return results.toArray(new String[results.size()]);
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            }
    }
}

