package com.example.najish.chatting.service;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.najish.chatting.dao.ChatDao;
import com.example.najish.chatting.dao.RegisterDao;
import com.example.najish.chatting.model.Chat;
import com.example.najish.chatting.model.RegisterModel;

@Database(entities = {Chat.class,RegisterModel.class} , version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();
    public abstract RegisterDao registerDao();
}
