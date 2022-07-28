package com.example.sked;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class MessageRoomDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();

    private static MessageRoomDatabase INSTANCE;

    public static MessageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null)
            synchronized (MessageRoomDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MessageRoomDatabase.class, "messagelist_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                    if (INSTANCE.messageDao().getAllRowsL().size() == 0) {
                        Gson gson = new Gson();
                        INSTANCE.messageDao().insert(new Message(null, "Hi! I'm Sked! Your personal assistant for relocation to Germany related questions. Ask me a question and I'll do my best to answer it.", "left", gson.toJson(Calendar.getInstance().getTime())));
                    }
                }
            }
        return INSTANCE;
    }
}
