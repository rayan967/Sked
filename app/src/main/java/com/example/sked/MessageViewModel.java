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
    static String jsonstring = "[\n" +
            "    {\n" +
            "        \"recipient_id\": \"user\",\n" +
            "        \"text\": \"Great! You are in Germany. Dont worry, I will help you.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"recipient_id\": \"user\",\n" +
            "        \"text\": \"You need to complete the following 3 formalities after moving to Germany.\\n 1. City Registration (To know more About this option:Enter > 1)\\n 2. Open Bank Account (To know more About this option:Enter > 2)\\n 3. Activate Health Insurance (To know more About this option:Enter > 3)\"\n" +
            "    }\n" +
            "]";
    LiveData<List<Message>> mAllRows;
    MutableLiveData<List<Message>> messagelist = new MutableLiveData<>();

    public MessageViewModel(Application app) {
        super(app);
        //   mAllRows = reference to API code
    }

    public LiveData<List<Message>> getAllRows() {
        return mAllRows;
    }

    public  String[] sendbotmessage(String message) throws InterruptedException, ExecutionException {
        new PopulateDbAsync(message).execute().get();
        return strarr;
    }


    private class PopulateDbAsync extends AsyncTask<Void, Void, Void> {


        String message;

        PopulateDbAsync(String message) {

            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.isCancelled()) {
                result = null;
                return;
            }
        }


        @Override
        protected Void doInBackground(final Void... params) {
            try {




                String url="http://2cd8-88-209-32-73.ngrok.io/webhooks/rest/webhook";
                OkHttpClient client = new OkHttpClient();
                client.setConnectTimeout(60, TimeUnit.SECONDS);
                client.setWriteTimeout(60, TimeUnit.SECONDS);
                client.setReadTimeout(60, TimeUnit.SECONDS);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonify(message).toString());
                Request request = new Request.Builder()
                        .get()
                        .post(body)
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();


              //  String jsonstring = MessageViewModel.jsonstring;
                String jsonstring=response.body().string();


                Log.d("Response ", jsonstring);
                JSONArray results=new JSONArray(jsonstring);

                String[] arr=new String[results.length()];
                for (int i = 0; i < results.length(); i++) {

                    JSONObject m=results.getJSONObject(i);
                    String m2=m.getString("text");
                    arr[i]=m2;
                    Log.d("message:",m2);
                }
                strarr=arr;


        }

            catch(
        Exception e)

        {


            e.printStackTrace();
        }
            return null;
    }
    public JSONObject jsonify(String message) throws JSONException
    {
        JSONObject js=new JSONObject();
        js.put("sender","user");
        js.put("message",message);
        return js;
    }
}


}