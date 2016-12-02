package com.example.kim.triple.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.TripLocation;

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
    public void test(){Log.i("Mission test log","");}
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
            Log.i("Mission insert log","");
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

    public void insert(Mission mission){
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            db.execSQL("INSERT INTO " + DatabaseConnection.MISSION_TABLE_NAME + " (endTime, latitude, longitude, classification, tripLocationId, name, explan, imageUrl) VALUES(" +
                    "\"" + mission.getEndTime() + "\"" + "," +
                    "\"" + mission.getLatitude() + "\"" + "," +
                    "\"" + mission.getLongitude() + "\"" + "," +
                    "\"" + mission.getClassification() + "\"" + "," +
                    "\"" + mission.getTripLocationId() + "\""+ ","+
                    "\"" + mission.getName() + "\""+ ","+
                    "\"" + mission.getExplan() + "\""+ ","+
                    "\"" + mission.getImageUrl() + "\""+ ");");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    public List<Mission> selectFromTripId(int tid) {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            List<Mission> missionList = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseConnection.MISSION_TABLE_NAME + " where tripLocationId = '"+tid+"'", null);
            while (cursor.moveToNext()) {
                missionList.add( new Mission()
                        .setId(cursor.getInt(0))
                        .setEndTime(cursor.getInt(1))
                        .setLatitude(cursor.getString(2))
                        .setLongitude(cursor.getString(3))
                        .setClassification(cursor.getInt(4))
                        .setTripLocationId(cursor.getInt(5))
                        .setName(cursor.getString(6))
                        .setExplan(cursor.getString(7))
                        .setImageUrl(cursor.getString(8))
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

    public Mission selectFromId(int id) {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            Mission mission = new Mission();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseConnection.MISSION_TABLE_NAME + " where _id = '"+id+"'", null);
            while (cursor.moveToNext()) {
                mission = new Mission()
                        .setId(cursor.getInt(0))
                        .setEndTime(cursor.getInt(1))
                        .setLatitude(cursor.getString(2))
                        .setLongitude(cursor.getString(3))
                        .setClassification(cursor.getInt(4))
                        .setTripLocationId(cursor.getInt(5))
                        .setName(cursor.getString(6))
                        .setExplan(cursor.getString(7))
                        .setImageUrl(cursor.getString(8));
            }

            return mission;
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
