package com.example.demo2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String fileName;
    private static Logger instance;
    private Logger(){
        fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        createFile();
    }
    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }
    private void createFile() {
        try{
            if(!Files.exists(Path.of("./logs/"))){
                Files.createDirectory(Path.of("./logs/"));
            }
            Files.createFile(Path.of("./logs/" + fileName + ".txt"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void logError(String message, String className, String methodName){
        try{
            Files.writeString(Path.of("./logs/" + fileName + ".txt"), "\nERROR: " + message  + "  |  at " + className + " - " + methodName + "\n \n", java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void logInfo(String message, String className, String methodName){
        try{
            Files.writeString(Path.of("./logs/" + fileName + ".txt"), "INFO: " + message + "  |  at " + className + " - " + methodName +"\n", java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void logWarning(String message, String className, String methodName){
        try{
            Files.writeString(Path.of("./logs/" + fileName + ".txt"), "WARNING: " + message + "  |  at " + className + " - " + methodName  + "\n", java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
