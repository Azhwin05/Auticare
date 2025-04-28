package com.example.auticare;

public class Message {
    private String senderId;
    private String messageText;
    private long timestamp;  // Changed to long

    // Default constructor for Firebase
    public Message() {
    }

    // Constructor without timestamp (auto handled)
    public Message(String senderId, String messageText) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = System.currentTimeMillis(); // Auto assign current time
    }

    // Constructor with all three fields
    public Message(String senderId, String messageText, long timestamp) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    // Getters
    public String getSenderId() {
        return senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters (Optional but safe)
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
