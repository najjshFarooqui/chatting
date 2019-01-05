package com.example.najish.chatting.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class RegisterModel {
    @NonNull
    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public String userName;
    public String userEmail;
    public String userPassword;


public RegisterModel(){
}
}
