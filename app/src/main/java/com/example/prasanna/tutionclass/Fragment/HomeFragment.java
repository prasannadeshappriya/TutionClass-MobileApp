package com.example.prasanna.tutionclass.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.prasanna.tutionclass.Adapter.LessonAdapter;
import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import java.util.ArrayList;

/**
 * Created by prasanna on 8/14/17.
 */

public class HomeFragment extends Fragment {
    private ListView lstLessons;
    private Button btnAddLesson;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //init variables
        init(view);
        initArrayList();

        btnAddLesson.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCreateEventWindow();
                    }
                }
        );
        return view;
    }

    private void openCreateEventWindow() {
        AddLessonFragment addLessonFragment = new AddLessonFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frmMain, addLessonFragment);
        transaction.commit();
    }

    private void initArrayList() {
        ArrayList<Lesson> arrLesson = new ArrayList<>();
        arrLesson.add(new Lesson(1,"Prasanna","","6G",45,"2017-08-04"));
        arrLesson.add(new Lesson(1,"Prasanna","","6G",45,"2017-08-04"));
        LessonAdapter adapter = new LessonAdapter(arrLesson, getContext());
        lstLessons.setAdapter(adapter);
    }

    private void init(View view) {
        lstLessons = (ListView) view.findViewById(R.id.lstLessons);
        btnAddLesson = (Button) view.findViewById(R.id.btnAddLesson);
    }
}
