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

    //Months
    public static final ArrayList<String> MONTH_LIST_SPINNER = new ArrayList<String>(){{
        add("All");add("January");add("February");add("March");add("April");add("May");add("June");
        add("July");add("August");add("September");add("October");add("November");add("December");
    }};

    //Years
    public static final ArrayList<String> YEAR_LIST = new ArrayList<String>(){{
        add("All");
        add("2017");add("2018");add("2019");add("2020");add("2021");
        add("2022");add("2023");add("2024");add("2025");add("2026");
        add("2027");add("2028");add("2029");add("2030");add("2031");
        add("2032");add("2033");add("2034");add("2035");add("2036");
        add("2037");add("2038");add("2039");add("2040");
    }};
}
