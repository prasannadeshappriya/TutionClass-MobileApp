package com.example.prasanna.tutionclass;

import android.util.Log;

/**
 * Created by prasanna on 8/13/17.
 */

public abstract class Constants {
    //App data constants
    public static final String APP_NAME = "TutionClass";
    public static final String APP_VERSION = "1.0";

    //Database configurations
    public static final String DATABASE_NAME = "tution_class";
    public static final int DATABASE_VERSION = 1;
    public static final String USER_TABLE_NAME = "user";

    //Debug
    public static final String TAG = "TutionClass";
    public static void printLog(String message){Log.i(TAG, message);}
}
