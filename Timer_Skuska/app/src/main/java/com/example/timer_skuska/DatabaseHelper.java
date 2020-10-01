package com.example.timer_skuska;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "workoutlist.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " +
                Workouts.WorkoutsEntry.TABLE_NAME + " (" +
                Workouts.WorkoutsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Workouts.WorkoutsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                Workouts.WorkoutsEntry.COLUMN_WORK + " TEXT NOT NULL, " +
                Workouts.WorkoutsEntry.COLUMN_REST + " TEXT NOT NULL, " +
                Workouts.WorkoutsEntry.COLUMN_ROUNDS + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Workouts.WorkoutsEntry.TABLE_NAME);
        onCreate(db);
    }
}
