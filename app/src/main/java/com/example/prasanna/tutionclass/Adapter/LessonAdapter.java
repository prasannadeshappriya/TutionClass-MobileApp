package com.example.prasanna.tutionclass.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import java.util.ArrayList;

/**
 * Created by prasanna on 8/14/17.
 */

public class LessonAdapter extends BaseAdapter {
    private ArrayList<Lesson> arrLessons;
    private Context context;

    public LessonAdapter(ArrayList<Lesson> arrLessons, Context context) {
        this.arrLessons = arrLessons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrLessons.size();
    }

    @Override
    public Object getItem(int position) {
        return arrLessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrLessons.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.adapter_lesson, null);

        //Initialize Variables
        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        TextView tvGrades = (TextView)view.findViewById(R.id.tvGrades);
        TextView tvCount = (TextView)view.findViewById(R.id.tvCount);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);

        Lesson lesson = arrLessons.get(position);
        String dateArr[] = lesson.getDate().split("-");
        String date = dateArr[0] + "-" +
                Constants.MONTH_LIST.get(Integer.parseInt(dateArr[1])) + "-" +
                dateArr[2];

        tvName.setText("Name: " + lesson.getName());
        tvGrades.setText("Grade: " + lesson.getGrade());
        tvCount.setText("Student Count: " + lesson.getStudent_count());
        tvDate.setText("Date: " + date);

        return view;
    }
}
