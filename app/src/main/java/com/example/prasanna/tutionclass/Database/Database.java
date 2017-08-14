package com.example.prasanna.tutionclass.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.prasanna.tutionclass.Constants;

/**
 * Created by prasanna on 8/13/17.
 */

public class Database extends SQLiteOpenHelper {
    private String sqlCommand;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqlCommand = "CREATE TABLE IF NOT EXISTS user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(50), " +
                "email VARCHAR(100), " +
                "password VARCHAR(255), " +
                "login_status INTEGER DEFAULT 0);";
        Constants.printLog("Create user table: " + sqlCommand);
        db.execSQL(sqlCommand);

        sqlCommand = "CREATE TABLE IF NOT EXISTS lesson (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "name VARCHAR(50), " +
                "comments VARCHAR(255), " +
                "grade VARCHAR(10), " +
                "date DATE, " +
                "student_count INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES user(id));";
        Constants.printLog("Create lesson table: " + sqlCommand);
        db.execSQL(sqlCommand);

        sqlCommand = "CREATE TABLE IF NOT EXISTS feedback (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "lesson_id INTEGER, " +
                "flag VARCHAR(10), " +
                "FOREIGN KEY(user_id) REFERENCES user(id), " +
                "FOREIGN KEY(lesson_id) REFERENCES lesson(id));";
        Constants.printLog("Create lesson table: " + sqlCommand);
        db.execSQL(sqlCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqlCommand = "DROP TABLE IF EXISTS user;";
        Constants.printLog("Drop user table: " + sqlCommand);
        db.execSQL(sqlCommand);
        sqlCommand = "DROP TABLE IF EXISTS lesson;";
        Constants.printLog("Drop lesson table: " + sqlCommand);
        db.execSQL(sqlCommand);
        sqlCommand = "DROP TABLE IF EXISTS feedback;";
        Constants.printLog("Drop feedback table: " + sqlCommand);
        db.execSQL(sqlCommand);
        onCreate(db);
    }

    public SQLiteDatabase getDatabase(){return getWritableDatabase();}
}
