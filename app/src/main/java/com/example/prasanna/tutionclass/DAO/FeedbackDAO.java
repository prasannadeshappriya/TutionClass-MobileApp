package com.example.prasanna.tutionclass.DAO;

import android.content.ContentValues;
import android.content.Context;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.Models.Feedback;
import com.example.prasanna.tutionclass.Models.Lesson;

import static com.example.prasanna.tutionclass.Constants.printLog;

/**
 * Created by prasanna_d on 8/15/2017.
 */
//"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//        "user_id INTEGER, " +
//        "lesson_id INTEGER, " +
//        "flag VARCHAR(10), " +
//        "FOREIGN KEY(user_id) REFERENCES user(id), " +
//        "FOREIGN KEY(lesson_id) REFERENCES lesson(id));";
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
}
