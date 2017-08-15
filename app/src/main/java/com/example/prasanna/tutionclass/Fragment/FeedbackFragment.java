package com.example.prasanna.tutionclass.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.prasanna.tutionclass.Adapter.LessonAdapter;
import com.example.prasanna.tutionclass.DAO.LessonDAO;
import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import java.util.ArrayList;

/**
 * Created by prasanna on 8/14/17.
 */

public class FeedbackFragment extends Fragment {
    private ListView lstLessonsFeedback;
    private LessonDAO lesson_dao;
    private String email;
    private String user_name;
    private String user_id;

    public void setUserDetails(String email, String user_name, String user_id) {
        this.email = email;
        this.user_name = user_name;
        this.user_id = user_id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        //init variables
        init(view);
        initArrayList();

        lstLessonsFeedback.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Lesson lesson = (Lesson) adapterView.getItemAtPosition(i);
                        openCreateEventWindow(true, lesson);
                        return false;
                    }
                }
        );

        return view;
    }

    private void openCreateEventWindow(boolean isEdit, Lesson lesson) {
        FeedbackViewFragment feedbackViewFragment = new FeedbackViewFragment();
        feedbackViewFragment.setUserDetails(email, user_name, user_id);
        feedbackViewFragment.setLesson(lesson);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frmMain, feedbackViewFragment);
        transaction.commit();
    }

    private void initArrayList() {
        ArrayList<Lesson> arrLesson = lesson_dao.getLessonArray(user_id);
        LessonAdapter adapter = new LessonAdapter(arrLesson, getContext());
        lstLessonsFeedback.setAdapter(adapter);
    }

    private void init(View view) {
        lstLessonsFeedback = (ListView)view.findViewById(R.id.lstLessonsFeedback);
        lesson_dao = new LessonDAO(getContext());
    }
}
