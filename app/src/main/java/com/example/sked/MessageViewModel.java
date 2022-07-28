package com.example.sked;

import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MessageViewModel extends AndroidViewModel {
    static String[] strarr;
    MessageRepository mRepository;
    LiveData<List<Message>> mAllRows;
    MutableLiveData<List<Message>> messagelist = new MutableLiveData<>();

    public MessageViewModel(Application app) {
        super(app);
        mRepository=new MessageRepository(app);
        mAllRows=mRepository.getAllRows();
    }

    public void clear()
    {
        mRepository.clear();
    }

    public void sendmessage(String message){
        mRepository.sendmessage(message);
        mAllRows=mRepository.getAllRows();
    }

    public LiveData<List<Message>> getAllRows() {
        return mAllRows;
    }


}