package com.example.kim.triple.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
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
    public static final String MISSION_CART_TABLE_NAME = "mission_cart";

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
    private void checkFolder()
    {
         File folder = new File(ROOT_DIR);
         File file = new File(ROOT_DIR + "/" + DB_NAME);

        try {
            if (folder.exists()) {
            } else {
                folder.mkdirs();
            }

            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean open()
    {
        if(dbHelper == null)
        {
            checkFolder();
                dbHelper = new DatabaseHelper(context);
                db = dbHelper.getReadableDatabase();
               // dbHelper.onCreate(db);
                Log.e("aaa","bbbb");
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


    public void clear(){
        if(dbHelper!=null){
            db = dbHelper.getReadableDatabase();
            dbHelper.initTable(db);
        }
        else{
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getReadableDatabase();
            dbHelper.initTable(db);
        }
    }
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, TABLE_VERSION);
            Log.i("dir",""+Environment.getExternalStorageDirectory());
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createUserTable(db);
            createMissionTable(db);
            createMissionCartTable(db);
            createTripLocationTable(db);
            Log.e("DATABASE_CONNECTION", "onCreate");
        }

        private void createUserTable(SQLiteDatabase db){
            String DROP_SQL = "DROP TABLE if exists " + USER_TABLE_NAME;
            String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " (" +
                    "_id integer not null primary key autoincrement," +
                    "name Char(128)," +
                    "distance integer," +
                    "level integer)";
            db.execSQL(DROP_SQL);
            db.execSQL(CREATE_SQL);
        }

        private void createMissionTable(SQLiteDatabase db) {
            String DROP_SQL = "DROP TABLE if exists " + MISSION_TABLE_NAME;
            String CREATE_SQL =
                    "CREATE TABLE IF NOT EXISTS " + MISSION_TABLE_NAME + " (" +
                            "_id integer not null primary key autoincrement,"+
                            "endTime integer,"+
                            "latitude Char(128)," +
                            "longitude Char(128)," +
                            "classification Char(128)," +
                            "tripLocationId integer,"+
                            "name Char(128),"+
                            "explan Char(128),"+
                            "imageUrl Char(128));";

            db.execSQL(DROP_SQL);
            db.execSQL(CREATE_SQL);
        }

        private void createMissionCartTable(SQLiteDatabase db) {
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

        private void createTripLocationTable(SQLiteDatabase db) {
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
            Log.i("DATABASE_Trip", DROP_SQL);
            db.execSQL(CREATE_SQL);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
        private void initTable(SQLiteDatabase db){
            createUserTable(db);
            createMissionTable(db);
            createMissionCartTable(db);
            createTripLocationTable(db);
        }

    }
}
