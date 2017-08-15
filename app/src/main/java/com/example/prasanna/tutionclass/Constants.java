package com.example.prasanna.tutionclass;

import android.util.Log;

import java.util.ArrayList;

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
    public static final String LESSON_TABLE_NAME = "lesson";
    public static final String FEEDBACK_TABLE_NAME = "feedback";

    //Debug
    public static final String TAG = "TutionClass";
    public static void printLog(String message){Log.i(TAG, message);}

    //Months
    public static final ArrayList<String> MONTH_LIST = new ArrayList<String>(){{
        add("January");add("February");add("March");add("April");add("May");add("June");
        add("July");add("August");add("September");add("October");add("November");add("December");
    }};
}
