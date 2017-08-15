package com.example.prasanna.tutionclass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.Models.Lesson;

import java.util.ArrayList;

import static com.example.prasanna.tutionclass.Constants.printLog;

/**
 * Created by prasanna_d on 8/15/2017.
 */

public class LessonDAO extends DAO {
    private Context context;
    private String tableName = Constants.LESSON_TABLE_NAME;
    private String command;

    public LessonDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void addLesson(Lesson lesson){
        ContentValues contentValues = new ContentValues();

        contentValues.put("user_id",lesson.getUser_id());
        contentValues.put("name",lesson.getName());
        contentValues.put("comments",lesson.getComments());
        contentValues.put("grade",lesson.getGrade());
        contentValues.put("date",lesson.getDate());
        contentValues.put("student_count",lesson.getStudent_count());
        sqldb.insert(
                tableName,
                null,
                contentValues
        );
        printLog("Lesson successfully inserted");
    }

    public ArrayList<Lesson> getLessonArray(String user_id){
        command = "SELECT * FROM " + tableName + " WHERE 1;";
        printLog("Select all lessons for id: " + "all");
        printLog(command);
        Cursor c = sqldb.rawQuery(command,null);
        printLog("Cursor count: " + c.getCount());

        ArrayList<Lesson> arrLesson = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                arrLesson.add(
                        new Lesson(
                                Long.parseLong(c.getString(c.getColumnIndex("user_id"))),
                                c.getString(c.getColumnIndex("name")),
                                c.getString(c.getColumnIndex("comments")),
                                c.getString(c.getColumnIndex("grade")),
                                Integer.parseInt(c.getString(c.getColumnIndex("student_count"))),
                                c.getString(c.getColumnIndex("date")),
                                Long.parseLong(c.getString(c.getColumnIndex("id")))
                        ));
            } while (c.moveToNext());
        }
        return arrLesson;
    }
}
