package com.example.najish.chatting;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.najish.chatting.adapter.MessageAdapter;
import com.example.najish.chatting.dao.ChatDao;
import com.example.najish.chatting.model.Chat;
import com.example.najish.chatting.service.MyNagoriApplication;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ChatDao chatDao;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatDao = MyNagoriApplication.getDatabase().chatDao();
        mAuth = FirebaseAuth.getInstance();
        //if internet is on
        displayChatMessages();
        //else
        chatDao.getAll().observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(@Nullable List<Chat> messages) {
                RecyclerView messageView = (RecyclerView) findViewById(R.id.list_of_messages);
                messageAdapter=new MessageAdapter(messages);
                messageView.setAdapter(messageAdapter);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database

                Chat chatMessages = new Chat(input.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getDisplayName());
                reference = FirebaseDatabase.getInstance().getReference().child("chat");
                reference.push().setValue(chatMessages);
                chatDao.insert(chatMessages);

                // Clear the input
                input.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            redirectUser();
        }

        return true;
    }

    public void redirectUser() {
        startActivity(new Intent(MainActivity.this, StartActivity.class));
    }

    private void displayChatMessages() {


    }
}

