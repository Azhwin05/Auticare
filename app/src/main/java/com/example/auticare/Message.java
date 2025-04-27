package com.example.auticare;

public class Message {
    private String senderId;
    private String messageText;
    private String timestamp;  // Optional: You can add timestamp if needed

    // Constructor with two arguments
    public Message(String senderId, String messageText) {
        this.senderId = senderId;
        this.messageText = messageText;
    }

    // Constructor with three arguments (including timestamp)
    public Message(String senderId, String messageText, String timestamp) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    // Default constructor for Firebase (if using DataSnapshot)
    public Message() {
        // Default constructor required for Firebase
    }

    // Getters and Setters
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}