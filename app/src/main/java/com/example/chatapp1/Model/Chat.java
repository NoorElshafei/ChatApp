package com.example.chatapp1.Model;

public class Chat {
    private String message;
    private String sender;
    private String receiver;
    private boolean isseen;

    public Chat(String message, String sender, String receiver, boolean isseen) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.isseen = isseen;
    }
    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
