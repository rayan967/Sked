package com.example.sked;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessageRepository {
    Application app;
    private MessageDao mMessageListDao;

    private LiveData<List<Message>> mAllRows;
    MessageRoomDatabase db;

    public MessageRepository(Application app)
    {
        this.app=app;
        db = MessageRoomDatabase.getDatabase(app);
        mMessageListDao = db.messageDao();
   //     db.messageDao().deleteAll();
        mAllRows = mMessageListDao.getAllRows();
    }

    public LiveData<List<Message>> getAllRows() {
        return mAllRows;
    }


    public void sendmessage(String message){
        try{
        Gson gson = new Gson();
        db.messageDao().insert(new Message(message,null, "right", gson.toJson(Calendar.getInstance().getTime())));
        new PopulateDbAsync(message).execute().get();}
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clear()
    {
        db.messageDao().deleteAll();
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



                Gson gson = new Gson();
                String url="http://cbfb-88-209-32-73.ngrok.io/webhooks/rest/webhook";
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


                for (String i:arr) {
                    db.messageDao().insert(new Message(null,i, "left", gson.toJson(Calendar.getInstance().getTime())));
                }

            }

            catch(Exception e)
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
