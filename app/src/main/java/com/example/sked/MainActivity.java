package com.example.sked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {



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
        recyclerHolder = findViewById(R.id.recycler_gchat);
        sendButton = findViewById(R.id.button_gchat_send);
        editTextChatbox = findViewById(R.id.edit_gchat_message);

        messages.add(new Message(null, "Hi! I'm Sked. You're personal assistant for relocation to germany related questions. Ask me a question and I'll do my best to answer it.", "left", Calendar.getInstance().getTime()));
        messageListAdapter = new MessageListAdapter(this, messages);
        messageRecycler = findViewById(R.id.recycler_gchat);
        messageRecycler.setHasFixedSize(true);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageListAdapter = new MessageListAdapter(mContext, messages);
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag==0)  {
                    if(editTextChatbox.getText().toString().equals(""))
                        return;
                else
                        messages.add(new Message(String.valueOf(editTextChatbox.getText()),null, "right", Calendar.getInstance().getTime()));
                        messageRecycler.setAdapter(messageListAdapter);
                        messageRecycler.scrollToPosition(messages.size()-1);
                }
                else if(flag==1)
                    {
                        messages.remove(messages.size()-1);
                        messages.remove(messages.size()-1);
                        flag=0;
                    }
                sendMessage(String.valueOf(editTextChatbox.getText()));

                hideKeyboard(MainActivity.this);
            }
        });
        messageRecycler.setAdapter(messageListAdapter);
        messageRecycler.scrollToPosition(messages.size()-1);
    }
    public static void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null){
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void sendMessage(String userMessage){

        editTextChatbox.setText("");
        String arr[];
        try {
            arr=mViewModel.sendbotmessage(userMessage);
            for (String i:arr) {
                messages.add(new Message(null,i, "left", Calendar.getInstance().getTime()));
                messageRecycler.setAdapter(messageListAdapter);
                messageRecycler.scrollToPosition(messages.size()-1);
            }

        }catch (Exception e){
            Log.e("Exception",e.toString());
        }



/* Demo Code
        switch (i) {
            case 0: messages.add(new Message(null,"Sure, please select from the items below things you have already have", "left", Calendar.getInstance().getTime()));
                messages.add(new Message(null,  null, "Passport,Visa","center" ));
                messages.add(new Message(null,  null, "Enrollment Certificate,Anmeldung","center" ));
                flag=1;
                break;
            case 1:
                messages.add(new Message(null,"Looks like you haven't completed your anmeldung (city registration) yet, is this correct?", "left", Calendar.getInstance().getTime()));
                messageRecycler.setAdapter(messageListAdapter);
                messageRecycler.scrollToPosition(messages.size()-1);
                break;
            case 2: messages.add(new Message(null,"Anmeldung is a mandatory requirement for a bank account, I can give you more information about it if you need", "left", Calendar.getInstance().getTime()));
                break;
            case 3: messages.add(new Message(null,"You're welcome, have a nice day!", "left", Calendar.getInstance().getTime()));
                break;
        }
        i++;
*/
    }
}