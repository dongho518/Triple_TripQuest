package com.example.kim.triple.data.dao;

import android.content.Context;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.MissionCart;

import static com.example.kim.triple.R.drawable.mission;

/**
 * Created by sihon on 2016-12-01.
 */

public class MissionCartDao {
    private Context context;
    public MissionCartDao(Context context){
        this.context = context;
    }

    public void insert(MissionCart missionCart) throws Exception {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            db.execSQL("INSERT INTO " + DatabaseConnection.MISSION_CART_TABLE_NAME + " (userId, missionId, missionResult) VALUES(" +
                    "\"" + missionCart.getUserId() + "\"" + "," +
                    "\"" + missionCart.getMissionId() + "\"" + "," +
                    "\"" + missionCart.getMissionResult() + "\""+ ");");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
}
