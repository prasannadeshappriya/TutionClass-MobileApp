package com.example.prasanna.tutionclass.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prasanna.tutionclass.Adapter.LessonAdapter;
import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.DAO.LessonDAO;
import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prasanna on 8/14/17.
 */

public class HomeFragment extends Fragment {
    private ListView lstLessons;
    private Button btnAddLesson;
    private LessonDAO lesson_dao;
    private String email;
    private String user_name;
    private String user_id;
    private Spinner spiHomeMonth;
    private Spinner spiHomeYear;
    private ArrayList<Lesson> arrLesson;
    private boolean month_change = false;
    private int month;

    public void setUserDetails(String email, String user_name, String user_id) {
        this.email = email;
        this.user_name = user_name;
        this.user_id = user_id;
    }

    public void setMonth_change(boolean flag, int month){
        month_change = flag; this.month = month;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //init variables
        init(view);
        initArrayList();

        if(month_change){
            spiHomeMonth.setSelection(month+1);
            month_change = false;
        }

        btnAddLesson.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCreateEventWindow(false, null);
                    }
                }
        );

        lstLessons.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Lesson lesson = (Lesson) adapterView.getItemAtPosition(i);
                        openCreateEventWindow(true, lesson);
                        return false;
                    }
                }
        );

        spiHomeYear.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        search(spiHomeYear.getItemAtPosition(position).toString(),
                                spiHomeMonth.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );

        spiHomeMonth.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        search(spiHomeYear.getSelectedItem().toString(),
                                spiHomeMonth.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );
        return view;
    }

    private void openCreateEventWindow(boolean isEdit, Lesson lesson) {
        AddEditLessonFragment addEditLessonFragment = new AddEditLessonFragment();
        addEditLessonFragment.setUserDetails(email, user_name, user_id);
        addEditLessonFragment.setIsEditFlag(isEdit, lesson);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frmMain, addEditLessonFragment);
        transaction.commit();
    }

    private void initArrayList() {
        arrLesson = lesson_dao.getLessonArray(user_id);
        LessonAdapter adapter = new LessonAdapter(arrLesson, getContext());
        lstLessons.setAdapter(adapter);
        ArrayAdapter<String> spiAdapter;
        spiAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, Constants.MONTH_LIST_SPINNER);
        spiHomeMonth.setAdapter(spiAdapter);
        spiAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, Constants.YEAR_LIST);
        spiHomeYear.setAdapter(spiAdapter);

        String c_year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        int c_month = Calendar.getInstance().get(Calendar.MONTH);
        int index = 0;
        for(int i=0; i<Constants.YEAR_LIST.size(); i++){
            if(Constants.YEAR_LIST.get(i).equals(c_year)){
                index = i; break;
            }
        }
        spiHomeYear.setSelection(index);
        spiHomeMonth.setSelection(c_month + 1);
        search(spiHomeYear.getSelectedItem().toString(),
                spiHomeMonth.getSelectedItem().toString());
    }

    private void search(String year, String Month){
        if(year.equals("All") && Month.equals("All")) {
            LessonAdapter adapter = new LessonAdapter(arrLesson, getContext());
            lstLessons.setAdapter(adapter);
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
            lstLessons.setAdapter(adapter);
        }else if(Month.equals("All")){
            ArrayList<Lesson> tmpArrLesson = new ArrayList<>();
            for (int i = 0; i < arrLesson.size(); i++) {
                String date[] = arrLesson.get(i).getDate().split("-");
                if (date[0].equals(year)) {
                    tmpArrLesson.add(arrLesson.get(i));
                }
            }
            LessonAdapter adapter = new LessonAdapter(tmpArrLesson, getContext());
            lstLessons.setAdapter(adapter);
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
            lstLessons.setAdapter(adapter);
        }
    }

    private void init(View view) {
        lesson_dao = new LessonDAO(getContext());
        lstLessons = (ListView) view.findViewById(R.id.lstLessons);
        btnAddLesson = (Button) view.findViewById(R.id.btnAddLesson);
        spiHomeMonth = (Spinner) view.findViewById(R.id.spiHomeMonth);
        spiHomeYear = (Spinner) view.findViewById(R.id.spiHomeYear);
    }
}
