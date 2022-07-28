package com.example.sked;


import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {



    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_OPTION_BUTTON = 3;
    private static final int VIEW_TYPE_GIF_IMAGE = 4;

    private List<Message> messageList;
    private Context mContext;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MessageListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public MessageListAdapter(Context mContext, List<Message> messageList) {
        this.messageList = messageList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if(message.getSide().equals("right"))
            return VIEW_TYPE_MESSAGE_SENT;
        else if(message.getSide().equals("left"))
            return VIEW_TYPE_MESSAGE_RECEIVED;
        else if(message.getSide().equals("center"))
            return VIEW_TYPE_OPTION_BUTTON;
        else
            return VIEW_TYPE_GIF_IMAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_user, parent, false);
            return new UserMessageHolder(view);
        }
        else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_bot, parent, false);
            return new BotMessageHolder(view);
        }
        else if(viewType == VIEW_TYPE_OPTION_BUTTON){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_option_button, parent, false);
            return new OptionButtonHolder(view, mListener);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((UserMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((BotMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_OPTION_BUTTON:
                ((OptionButtonHolder) holder).bind(message);
                break;
        }
    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private class UserMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        public UserMessageHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.text_message_body);
            timeText = view.findViewById(R.id.text_message_time);
        }

        void bind(Message message){
            messageText.setText(Html.fromHtml(message.getUserMessage()));
            DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            Gson gson = new Gson();
            Date myClass = gson.fromJson(message.getCurrentTime(), Date.class);
            timeText.setText(dateFormat.format(myClass));
        }
    }

    private class BotMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        public BotMessageHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.text_message_body);
            timeText = view.findViewById(R.id.text_message_time);
        }

        void bind(Message message){
            messageText.setText(Html.fromHtml(message.getBotMessage()));
            DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            Gson gson = new Gson();
            Date myClass = gson.fromJson(message.getCurrentTime(), Date.class);
            timeText.setText(dateFormat.format(myClass));
        }

    }

    private class OptionButtonHolder extends RecyclerView.ViewHolder{
        MaterialButton messageText;
        MaterialCheckBox cb1;
        MaterialCheckBox cb2;
        public OptionButtonHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            cb1 = itemView.findViewById(R.id.checkBox);
            cb2 = itemView.findViewById(R.id.checkBox2);



        }

        void bind(Message message){
            String[] split=message.getOptionMessage().split(",");
            cb1.setText(split[0]);
            cb2.setText(split[1]);
        }
    }

    public void setRows(List<Message> alList) {
        this.messageList = alList;
        notifyDataSetChanged();
    }



}
