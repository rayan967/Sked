package com.example.sked;

import java.util.Date;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "message_list")
public class Message {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "anime_id")
    @NonNull
    private int id;

    @ColumnInfo(name = "userMessage")
    private String userMessage;

    @ColumnInfo(name = "botMessage")
    private String botMessage;

    @ColumnInfo(name = "optionMessage")
    private String optionMessage;

    @ColumnInfo(name = "side")
    private String side;

    @ColumnInfo(name = "currentTime")
    private String currentTime;


    public Message(String userMessage, String botMessage, String side, String currentTime) {
        this.userMessage = userMessage;
        this.botMessage = botMessage;
        this.side = side;
        this.currentTime = currentTime;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
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



    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
