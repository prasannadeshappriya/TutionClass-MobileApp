package com.example.prasanna.tutionclass.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.prasanna.tutionclass.Adapter.LessonAdapter;
import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.DAO.LessonDAO;
import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by prasanna on 8/14/17.
 */

public class FeedbackFragment extends Fragment {
    private ListView lstLessonsFeedback;
    private LessonDAO lesson_dao;
    private String email;
    private String user_name;
    private String user_id;
    private ArrayList<Lesson> arrLesson;
    private Spinner spiMonth;
    private Spinner spiYear;


    public void setUserDetails(String email, String user_name, String user_id) {
        this.email = email;
        this.user_name = user_name;
        this.user_id = user_id;
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        //init variables
        init(view);
        initArrayList();
        closeKeyboard();

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

        spiYear.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        search(spiYear.getItemAtPosition(position).toString(),
                                spiMonth.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );

        spiMonth.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        search(spiYear.getSelectedItem().toString(),
                                spiMonth.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
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
        arrLesson = lesson_dao.getLessonArray(user_id);
        LessonAdapter adapter = new LessonAdapter(arrLesson, getContext());
        lstLessonsFeedback.setAdapter(adapter);
        ArrayAdapter<String> spiAdapter;
        spiAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, Constants.MONTH_LIST_SPINNER);
        spiMonth.setAdapter(spiAdapter);
        spiAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, Constants.YEAR_LIST);
        spiYear.setAdapter(spiAdapter);

        String c_year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        int c_month = Calendar.getInstance().get(Calendar.MONTH);
        int index = 0;
        for(int i=0; i<Constants.YEAR_LIST.size(); i++){
            if(Constants.YEAR_LIST.get(i).equals(c_year)){
                index = i; break;
            }
        }
        spiYear.setSelection(index);
        spiMonth.setSelection(c_month + 1);
        search(spiYear.getSelectedItem().toString(),
                spiMonth.getSelectedItem().toString());
    }

    private void search(String year, String Month){
        if(year.equals("All") && Month.equals("All")) {
            LessonAdapter adapter = new LessonAdapter(arrLesson, getContext());
            lstLessonsFeedback.setAdapter(adapter);
        }else if(year.equals("All")) {
            ArrayList<Lesson> tmpArrLesson = new ArrayList<>();
            for (int i = 0; i < arrLesson.size(); i++) {
                String date[] = arrLesson.get(i).getDate().split("-");
                int index = 0;
                for (int j = 0; j < Constants.MONTH_LIST.size(); j++) {
                    if (Constants.MONTH_LIST.get(j).equals(Month)) {
                        index = j;
                        break;
                    }
                }
                if (date[1].equals(String.valueOf(index))) {
                    tmpArrLesson.add(arrLesson.get(i));
                }
            }
            LessonAdapter adapter = new LessonAdapter(tmpArrLesson, getContext());
            lstLessonsFeedback.setAdapter(adapter);
        }else if(Month.equals("All")){
            ArrayList<Lesson> tmpArrLesson = new ArrayList<>();
            for (int i = 0; i < arrLesson.size(); i++) {
                String date[] = arrLesson.get(i).getDate().split("-");
                if (date[0].equals(year)) {
                    tmpArrLesson.add(arrLesson.get(i));
                }
            }
            LessonAdapter adapter = new LessonAdapter(tmpArrLesson, getContext());
            lstLessonsFeedback.setAdapter(adapter);
        }else {
            ArrayList<Lesson> tmpArrLesson = new ArrayList<>();
            for (int i = 0; i < arrLesson.size(); i++) {
                String date[] = arrLesson.get(i).getDate().split("-");
                int index = 0;
                for (int j = 0; j < Constants.MONTH_LIST.size(); j++) {
                    if (Constants.MONTH_LIST.get(j).equals(Month)) {
                        index = j;
                        break;
                    }
                }
                if (date[0].equals(year) && date[1].equals(String.valueOf(index))) {
                    tmpArrLesson.add(arrLesson.get(i));
                }
            }
            LessonAdapter adapter = new LessonAdapter(tmpArrLesson, getContext());
            lstLessonsFeedback.setAdapter(adapter);
        }
    }

    private void init(View view) {
        lstLessonsFeedback = (ListView)view.findViewById(R.id.lstLessonsFeedback);
        lesson_dao = new LessonDAO(getContext());
        spiYear = (Spinner) view.findViewById(R.id.spiYear);
        spiMonth = (Spinner) view.findViewById(R.id.spiMonth);
    }
}
