package com.example.kim.triple.data.dao;

import android.content.Context;

import com.example.kim.triple.data.DatabaseConnection;
import com.example.kim.triple.data.model.TripLocation;

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
}
