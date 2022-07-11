package com.example.sked;

import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import java.util.HashMap;
import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    LiveData<List<Message>> mAllRows;
    MutableLiveData<List<Message>> messagelist =  new MutableLiveData<>();
    public MessageViewModel(Application app)
    {
        super(app);
     //   mAllRows = reference to API code
    }

    public LiveData<List<Message>> getAllRows() {
        return mAllRows;
    }

}