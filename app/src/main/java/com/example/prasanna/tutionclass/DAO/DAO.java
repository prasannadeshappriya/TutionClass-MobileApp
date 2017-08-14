package com.example.prasanna.tutionclass.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.Database.Database;

/**
 * Created by prasanna on 8/13/17.
 */

public class DAO {
    public Database database;
    public SQLiteDatabase sqldb;

    public DAO(Context context){
        database = new Database(
                context,
                Constants.DATABASE_NAME,
                null,
                Constants.DATABASE_VERSION
        );
        sqldb = database.getDatabase();
    }
}
