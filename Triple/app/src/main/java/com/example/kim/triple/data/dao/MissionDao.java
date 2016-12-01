package com.example.kim.triple.data.dao;

import android.content.Context;
import android.database.Cursor;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.Mission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sihon on 2016-12-01.
 */

public class MissionDao {

    private final Context context;

    public MissionDao(Context context){
        this.context = context;
    }

    public List<Mission> selectAll() {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            List<Mission> missionList = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseConnection.MISSION_TABLE_NAME, null);
            while (cursor.moveToNext()) {
                missionList.add(new Mission()
                        .setEndTime(cursor.getInt(1))
                        .setLatitude(cursor.getString(2))
                        .setLongitude(cursor.getString(3))
                        .setClassification(cursor.getInt(4))
                        .setTripLocationId(cursor.getInt(5))
                        .setImageUrl(cursor.getString(6)));
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

    public void insert(Mission mission) throws Exception {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            db.execSQL("INSERT INTO " + DatabaseConnection.MISSION_TABLE_NAME + " (endTime, latitude, longitude, classification, tripLocationId) VALUES(" +
                    "\"" + mission.getEndTime() + "\"" + "," +
                    "\"" + mission.getLatitude() + "\"" + "," +
                    "\"" + mission.getLongitude() + "\"" + "," +
                    "\"" + mission.getClassification() + "\"" + "," +
                    "\"" + mission.getTripLocationId() + "\""+ "+"+
                    "\"" + mission.getImageUrl() + "\""+ ");");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
}
