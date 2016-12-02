package com.example.kim.triple.data.dao;

import android.content.Context;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.MissionCart;

/**
 * Created by khwbori on 2016. 12. 2..
 */

public class ClearDao {
    private Context context;
    public ClearDao(Context context){
        this.context = context;
    }

    public void clear() {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
}

