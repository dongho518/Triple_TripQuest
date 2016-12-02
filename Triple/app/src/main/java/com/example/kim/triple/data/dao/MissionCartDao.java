package com.example.kim.triple.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.MissionCart;

import java.util.ArrayList;
import java.util.List;

import static com.example.kim.triple.R.drawable.mission;

/**
 * Created by sihon on 2016-12-01.
 */

public class MissionCartDao {
    private Context context;
    public MissionCartDao(Context context){
        this.context = context;
    }

    public void test(){Log.i("missionCart test log","");}
    public void insert(MissionCart missionCart){
        Log.i("MissionCart Insert","");
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            db.execSQL("INSERT INTO " + DatabaseConnection.MISSION_CART_TABLE_NAME + " (userId, missionId, missionResult) VALUES(" +
                    "\"" + missionCart.getUserId() + "\"" + "," +
                    "\"" + missionCart.getMissionId() + "\"" + "," +
                    "\"" + missionCart.getMissionResult() + "\""+ ");");
            Log.i("Cart Insert","");
        } catch (Exception e) {
            Log.i("Cart Error","");
            e.printStackTrace();
        } finally {
            Log.i("Cart final","");
            if (db != null)
                db.close();
        }
    }

    public List<MissionCart> selectFromUserId(int uid) {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            List<MissionCart> missionList = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseConnection.MISSION_CART_TABLE_NAME + " where userId = '"+uid+"'", null);
            while (cursor.moveToNext()) {
                missionList.add( new MissionCart()
                        .setId(cursor.getInt(0))
                        .setUserId(cursor.getInt(1))
                        .setMissionId(cursor.getInt(2))
                        .setMissionResult(cursor.getInt(3))
                );
            }
            return missionList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return null;
    }
}
