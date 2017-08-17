package com.example.prasanna.tutionclass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.Models.LoginHistory;

import static com.example.prasanna.tutionclass.Constants.printLog;

/**
 * Created by prasanna_d on 8/17/2017.
 */

public class LoginHistoryDAO extends DAO{
    private Context context;
    private String tableName = Constants.LOGIN_HISTORY_TABLE_NAME;
    private String command;

    public LoginHistoryDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void updateLoginHistory(LoginHistory history){
        //Delete everything on the table
        command = "DELETE FROM " + tableName + " WHERE 1;";
        printLog("Delete all login history table");
        printLog(command);
        sqldb.execSQL(command);
        //Add new email to the database
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",history.getEmail());
        sqldb.insert(
                tableName,
                null,
                contentValues
        );
        printLog("history successfully inserted");
    }

    public LoginHistory getLoginHistory(){
        command = "SELECT * FROM " + tableName + " WHERE 1;";
        printLog("Select all login history from table: " + tableName);
        printLog(command);
        Cursor c = sqldb.rawQuery(command,null);
        printLog("Cursor count: " + c.getCount());

        LoginHistory history = null;
        if(c.moveToFirst()) {
            history = new LoginHistory(
                    Long.parseLong(c.getString(c.getColumnIndex("id"))),
                    c.getString(c.getColumnIndex("email"))
            );
        }
        return history;
    }
}
