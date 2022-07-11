package com.example.sked;

import java.util.Date;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "message_list")
public class Message {
    @PrimaryKey
    @ColumnInfo(name = "message_id")
    @NonNull
    private String id;
    @ColumnInfo(name = "user_message")
    private String userMessage;
    @ColumnInfo(name = "bot_message")
    private String botMessage;
    @ColumnInfo(name = "option_message")
    private String optionMessage;
    @ColumnInfo(name = "side")
    private String side;
    @ColumnInfo(name = "message_time")
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
