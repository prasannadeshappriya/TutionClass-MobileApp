package com.example.prasanna.tutionclass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.Models.Feedback;
import com.example.prasanna.tutionclass.Models.Lesson;

import java.util.ArrayList;

import static com.example.prasanna.tutionclass.Constants.printLog;

/**
 * Created by prasanna_d on 8/15/2017.
 */

public class FeedbackDAO extends DAO{
    private Context context;
    private String tableName = Constants.FEEDBACK_TABLE_NAME;
    private String command;

    public FeedbackDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void addFeedback(Feedback feedback){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id",feedback.getUser_id());
        contentValues.put("lesson_id",feedback.getEvent_id());
        contentValues.put("flag",feedback.getFlag());
        sqldb.insert(
                tableName,
                null,
                contentValues
        );
        printLog("Feedback successfully inserted");
    }

    public void deleteFeedback(Lesson lesson){
        command = "DELETE FROM " + tableName + " WHERE user_id=" + lesson.getUser_id() + " AND lesson_id=" + lesson.getId() + ";";
        printLog("Delete Feedback [id: " + lesson.getId() + ", user: " + lesson.getUser_id() + "]");
        printLog(command);
        sqldb.execSQL(command);
    }

    public ArrayList<Feedback> getFeedbackArray(Lesson lesson){
        command = "SELECT * FROM " + tableName + " WHERE user_id=" + lesson.getUser_id() + " AND lesson_id=" + lesson.getId() + ";";
        printLog("Select all lessons for id: " + lesson.getUser_id());
        printLog(command);
        Cursor c = sqldb.rawQuery(command,null);
        printLog("Cursor count: " + c.getCount());

        ArrayList<Feedback> arrFeedback = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                arrFeedback.add(
                        new Feedback(
                                Long.parseLong(c.getString(c.getColumnIndex("lesson_id"))),
                                c.getString(c.getColumnIndex("flag")),
                                Long.parseLong(c.getString(c.getColumnIndex("user_id")))
                        ));
            } while (c.moveToNext());
        }
        return arrFeedback;
    }
}
