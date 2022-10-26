package com.example.easyqueue.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {

    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();

    public static final String DATABASE_NAME = "EasyQueueUser.db";
    public static final String TABLE_NAME="User";
    public static final String Column_ID="id";
    public static final String Column_Name="name";
    public static final String Column_Email="email";
    public static final String Column_Mobile="mobileNo";
    public static final String Column_Address="address";
    public static final String Column_Role="role";
    public static final String Column_Password="password";
    public static final String Column_FuelType="fuelType";
    public static final String Column_ShedID="shedID";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ("+Column_ID+" INTEGER PRIMARY KEY, " +Column_Name+" VARCHAR, " +Column_Address+" VARCHAR, " +Column_Email+" VARCHAR, " +Column_Mobile+" VARCHAR, " +Column_Role+" VARCHAR, " +Column_Password+" VARCHAR,"+Column_FuelType+"VARCHAR,"+Column_ShedID+"VARCHAR)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
