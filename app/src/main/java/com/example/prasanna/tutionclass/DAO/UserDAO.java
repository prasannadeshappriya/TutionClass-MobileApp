package com.example.prasanna.tutionclass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.Models.User;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;

/**
 * Created by prasanna on 8/13/17.
 */

public class UserDAO extends DAO {
    private Context context;
    private String tableName = Constants.USER_TABLE_NAME;
    private String command;

    public UserDAO(Context context) {
        super(context);
        this.context = context;
    }

    public boolean isUserExist(String email){
        command = "SELECT * FROM " + tableName + " WHERE email=\"" + email + "\";";
        Constants.printLog("Check user: " + email + " exist, " + command);
        Cursor c = sqldb.rawQuery(command,null);
        if(c.getCount()>0){
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }
    }

    public User getLoginUser(){
        command = "SELECT * FROM " + tableName + " WHERE login_status=1;";
        Constants.printLog("Get all users with login_status=1");
        Constants.printLog(command);
        Cursor c = sqldb.rawQuery(command,null);
        Constants.printLog("Cursor result count: " + c.getCount());
        User user = null;
        if(c.moveToFirst()) {
            user = new User(
                    Long.parseLong(c.getString(c.getColumnIndex("id"))),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("email")),
                    c.getString(c.getColumnIndex("password"))
            );
        }
        return user;
    }

    public void setLoginStatusFlag(Boolean flag, String email){
        if(flag){
            command = "UPDATE " + tableName + " SET login_status=0;";
            Constants.printLog("Update and reset login_status flag for all users");
            Constants.printLog(command);
            sqldb.execSQL(command);
            command = "UPDATE " + tableName + " SET login_status=1 WHERE email=\"" + email + "\";";
            Constants.printLog("Update and set login_status flag true for users: " + email);
            Constants.printLog(command);
            sqldb.execSQL(command);
        }else{
            command = "UPDATE " + tableName + " SET login_status=0;";
            Constants.printLog("Update and reset login_status flag for all users");
            sqldb.execSQL(command);
        }
    }

    public User UserAuth(String email, String password){
        command = "SELECT * FROM " + tableName + " WHERE email=\"" + email + "\" AND password=\"" + password + "\";";
        Constants.printLog("Check for user with email: " + email +", password: "+password);
        Cursor c = sqldb.rawQuery(command,null);
        Constants.printLog("Cursor result count: "+c.getCount());

        User user = null;
        if(c.moveToFirst()) {
            user = new User(
                    Long.parseLong(c.getString(c.getColumnIndex("id"))),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("email")),
                    c.getString(c.getColumnIndex("password"))
            );
        }
        return user;
    }

    public void addUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",user.getName());
        contentValues.put("email",user.getEmail());
        contentValues.put("password",user.getPassword());
        sqldb.insert(
                tableName,
                null,
                contentValues
        );
        Constants.printLog("User successfully inserted");
    }
}
