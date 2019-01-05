package com.example.najish.chatting.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.najish.chatting.R;
import com.example.najish.chatting.model.Chat;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHolder> {
    List<Chat> messages;

    public MessageAdapter( List<Chat> messages){
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.message, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ItemHolder itemHolder, int i) {
        itemHolder.bindTo(messages.get(i));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView userName;
        EditText message;
        TextView time;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.message_user);
            message=itemView.findViewById(R.id.message_text);
            time=itemView.findViewById(R.id.message_time);
        }
        protected void bindTo(Chat items) {
            userName.setText(items.displayName);
            message.setText(items.message);
            time.setText(Long.toString(items.timeStamp));
        }
    }

}
