package com.example.kim.triple.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.TripLocation;

import java.util.ArrayList;
import java.util.List;

import static com.example.kim.triple.R.drawable.mission;

/**
 * Created by sihon on 2016-12-01.
 */

public class TripLocationDao {
    private Context context;
    public TripLocationDao(Context context){
        this.context = context;
    }

    public void insert(TripLocation tripLocation){
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            db.execSQL("INSERT INTO " + DatabaseConnection.TRIP_LOCATION_TABLE_NAME + " (name, picture, tag, phoneNumber, address) VALUES(" +
                    "\"" + tripLocation.getName() + "\"" + "," +
                    "\"" + tripLocation.getPicture() + "\"" + "," +
                    "\"" + tripLocation.getTag() + "\"" + "," +
                    "\"" + tripLocation.getPhoneNumber() + "\"" + "," +
                    "\"" + tripLocation.getAddress() + "\""+ ");");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    public List<TripLocation> selectAll() {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            List<TripLocation> tripLocationList = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseConnection.TRIP_LOCATION_TABLE_NAME, null);
            while (cursor.moveToNext()) {
                tripLocationList.add(new TripLocation()
                        .setId(cursor.getInt(0))
                        .setName(cursor.getString(1))
                        .setPicture(cursor.getString(2))
                        .setTag(cursor.getString(3))
                        .setPhoneNumber(cursor.getString(4))
                        .setAddress(cursor.getString(5)));
            }
            return tripLocationList;
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

    public TripLocation selectFromId(int id) {
        DatabaseConnection db = DatabaseConnection.getInstance(context);
        try {
            db.open();
            TripLocation tripLocation = new TripLocation();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseConnection.TRIP_LOCATION_TABLE_NAME + " where _id = '"+id+"'", null);
            while (cursor.moveToNext()) {
                tripLocation = new TripLocation()
                        .setId(cursor.getInt(0))
                        .setName(cursor.getString(1))
                        .setPicture(cursor.getString(2))
                        .setTag(cursor.getString(3))
                        .setPhoneNumber(cursor.getString(4))
                        .setAddress(cursor.getString(5));
            }
            Log.i("selet id",tripLocation.getName());
            return tripLocation;
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
