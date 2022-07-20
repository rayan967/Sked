package com.example.sked;

import java.util.Date;
import android.graphics.Bitmap;




public class Message {

    private String id;
    private String userMessage;
    private String botMessage;
    private String optionMessage;
    private String side;
    private Date currentTime;


    public Message(String userMessage, String botMessage, String side, Date currentTime) {
        this.userMessage = userMessage;
        this.botMessage = botMessage;
        this.side = side;
        this.currentTime = currentTime;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(String botMessage) {
        this.botMessage = botMessage;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOptionMessage() {
        return optionMessage;
    }

    public void setOptionMessage(String optionMessage) {
        this.optionMessage = optionMessage;
    }



    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
