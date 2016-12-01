package com.example.kim.triple.data.dao;

import android.content.Context;
import android.database.Cursor;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sihon on 2016-12-01.
 */

public class UserDao {
    private final Context context;

    public UserDao(Context context){
        this.context = context;
    }
}
