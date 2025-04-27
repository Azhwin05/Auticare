package com.example.auticare;

public class Message {
    private String userId;
    private String userName;
    private String message;

    // Empty constructor (important for Firebase)
    public Message() {}

    public Message(String userId, String userName, String message) {
        this.userId = userId;
        this.userName = userName;
        this.message = message;
    }

    // Getters
    public String getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getMessage() {
        return message;
    }

    //Setters
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    public void setUserName(final String userName) {
        this.userName = userName;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
}
