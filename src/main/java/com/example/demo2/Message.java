package com.example.demo2;

public class Message {
    private String sender;
    private String receiver;
    private String message;
    private String dateSent;

    public Message(String sender, String receiver, String message, String dateSent) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.dateSent = dateSent;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public String getDateSent() {
        return dateSent;
    }
}
