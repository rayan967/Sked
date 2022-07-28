package com.example.sked;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Gson gson;
    private Context mContext = MainActivity.this;
    private View recyclerHolder;
    private RecyclerView messageRecycler;
    private MessageListAdapter messageListAdapter;
    private List<Message> messages = new ArrayList<>();
    int i=0;
    int flag=0;
    private MaterialButton sendButton;
    private EditText editTextChatbox;
    private MessageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        recyclerHolder = findViewById(R.id.recycler_gchat);
        sendButton = findViewById(R.id.button_gchat_send);
        editTextChatbox = findViewById(R.id.edit_gchat_message);

        messageListAdapter = new MessageListAdapter(this, messages);
        messageRecycler = findViewById(R.id.recycler_gchat);
        messageRecycler.setHasFixedSize(true);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageListAdapter = new MessageListAdapter(mContext, messages);

        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);




        messageRecycler.setAdapter(messageListAdapter);
        messageRecycler.scrollToPosition(messageListAdapter.getItemCount()-1);


        mViewModel.getAllRows().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> words) {
                messageListAdapter.setRows(words);
                messageRecycler.setAdapter(messageListAdapter);
                messageRecycler.scrollToPosition(messageListAdapter.getItemCount()-1);

            }
        });



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextChatbox.getText().toString().equals(""))
                    return;
                else {
                    mViewModel.sendmessage(String.valueOf(editTextChatbox.getText()));

                }
                messageRecycler.setAdapter(messageListAdapter);
                messageRecycler.scrollToPosition(messageListAdapter.getItemCount()-1);


                editTextChatbox.setText("");
                hideKeyboard(MainActivity.this);
            }
        });



    }
    public static void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null){
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Clear chat":
                mViewModel.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}