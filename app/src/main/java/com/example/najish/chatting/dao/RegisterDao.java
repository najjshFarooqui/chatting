package com.example.najish.chatting.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.najish.chatting.model.RegisterModel;

import java.util.List;
@Dao
public interface RegisterDao {
    @Insert
    void insertAll(List<RegisterModel> chats);
}
