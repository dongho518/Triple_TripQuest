package com.example.kim.triple.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kim.triple.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by sihon on 2016-12-01.
 */

public class DatabaseConnection {
    public static final String ROOT_DIR = "/data/com.example.kim.triple/databases";
    public static final String DB_NAME = "triple.db";
    public static final String USER_TABLE_NAME = "user";
    public static final String MISSION_TABLE_NAME = "mission";
    public static final String TRIP_LOCATION_TABLE_NAME = "trip_location";
    public static final String MISSION_CART_TABLE_NAME = "misiion_cart";

    public static final int TABLE_VERSION = 1;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private static DatabaseConnection dbConnection;

    private DatabaseConnection(Context context)
    {
        this.context = context;
    }

    public static DatabaseConnection getInstance(Context context)
    {
        if(dbConnection == null){
            dbConnection = new DatabaseConnection(context);
        }

        return dbConnection;
    }

    public boolean open()
    {
        if(dbHelper == null)
        {
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getReadableDatabase();
        }
        if(db == null){
            return false;
        }
        return true;
    }

    public void close(){
        if(db == null){
            return;
        }
        db.close();
        dbHelper.close();
        dbHelper = null;
    }

    public Cursor rawQuery(String sql, String[] params)
    {
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(sql, params);
        } catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }

    public void execSQL(String sql){
        db.execSQL(sql);
    }
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, ROOT_DIR + "/" + DB_NAME, null, TABLE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createUserTable();
            createMissionTable();
            createMissionCartTable();
            createTripLocationTable();
        }

        private void createUserTable(){
            String DROP_SQL = "DROP TABLE if exists " + USER_TABLE_NAME;
            String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " (" +
                    "_id integer not null primary key autoincrement," +
                    "name Char(128)," +
                    "distance integer," +
                    "level integer)";
            db.execSQL(DROP_SQL);
            db.execSQL(CREATE_SQL);
        }

        private void createMissionTable() {
            String DROP_SQL = "DROP TABLE if exists " + MISSION_TABLE_NAME;
            String CREATE_SQL =
                    "CREATE TABLE IF NOT EXISTS " + MISSION_TABLE_NAME + " (" +
                            "_id integer not null primary key autoincrement,"+
                            "endTime integer,"+
                            "latitude Char(128)," +
                            "longitude Char(128)," +
                            "classification integer," +
                            "tripLocationId integer,"+
                            "imageUrl Char(128));";

            db.execSQL(DROP_SQL);
            db.execSQL(CREATE_SQL);
        }

        private void createMissionCartTable() {
            String DROP_SQL = "DROP TABLE if exists " + MISSION_CART_TABLE_NAME;
            String CREATE_SQL =
                    "CREATE TABLE IF NOT EXISTS " + MISSION_CART_TABLE_NAME + " (" +
                            "_id integer not null primary key autoincrement,"+
                            "userId integer," +
                            "missionId integer," +
                            "missionResult integer);";

            db.execSQL(DROP_SQL);
            db.execSQL(CREATE_SQL);
        }

        private void createTripLocationTable() {
            String DROP_SQL = "DROP TABLE if exists " + TRIP_LOCATION_TABLE_NAME;
            String CREATE_SQL =
                    "CREATE TABLE IF NOT EXISTS " + TRIP_LOCATION_TABLE_NAME + " (" +
                            "_id integer not null primary key autoincrement,"+
                            "name Char(128)," +
                            "picture Char(128)," +
                            "tag Char(128)," +
                            "phoneNumber Char(128)," +
                            "address Char(128));";

            db.execSQL(DROP_SQL);
            db.execSQL(CREATE_SQL);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}