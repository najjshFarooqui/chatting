package com.example.najish.chatting.service;

import android.app.Application;
import android.arch.persistence.room.Room;

public class MyNagoriApplication extends Application {
    final static String DATABASE_NAME = "db-newNagori";
    private static MyDatabase myDatabase;
    private static MyNagoriApplication myNagoriApplication;

    public static MyDatabase getDatabase() {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(myNagoriApplication,
                    MyDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myNagoriApplication = this;
    }
}
