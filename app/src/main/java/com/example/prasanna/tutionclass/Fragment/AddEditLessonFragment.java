package com.example.prasanna.tutionclass.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prasanna.tutionclass.DAO.LessonDAO;
import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import java.util.Date;

import static com.example.prasanna.tutionclass.Constants.MONTH_LIST;
import static com.example.prasanna.tutionclass.Constants.printLog;

/**
 * Created by prasanna on 8/14/17.
 */

public class AddEditLessonFragment extends Fragment {
    private EditText etName;
    private DatePicker dpPicker;
    private EditText etComments;
    private EditText etStuCount;
    private EditText etGrade;
    private Button btnAddLesson;
    private LessonDAO lesson_dao;
    private String email;
    private String user_name;
    private String user_id;
    private Lesson lesson;
    private boolean isEditData;

    public void setUserDetails(String email, String user_name, String user_id) {
        this.email = email;
        this.user_name = user_name;
        this.user_id = user_id;
    }

    public void setIsEditFlag(boolean flag, Lesson lesson){
        isEditData = flag;
        this.lesson = lesson;
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
        View view = inflater.inflate(R.layout.fragment_add_lesson, container, false);
        //Initialize XML variables
        init(view);
        closeKeyboard();

        if(isEditData){
            etComments.setText(lesson.getComments());
            etComments.setEnabled(false);
            String date[] = lesson.getDate().split("-");
            dpPicker.init(
                    Integer.parseInt(date[0]),
                    Integer.parseInt(date[1]),
                    Integer.parseInt(date[2]),null);
            dpPicker.setEnabled(false);
            etGrade.setText(lesson.getGrade());
            etGrade.setEnabled(false);
            etName.setText(lesson.getName());
            etName.setEnabled(false);
            etStuCount.setText(String.valueOf(lesson.getStudent_count()));
            etStuCount.setEnabled(false);
        }

        btnAddLesson.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addLesson();
                    }
                }
        );

        return view;
    }

    private void addLesson() {
        if(validate()) {
            String lesson_name = etName.getText().toString();
            String date = String.valueOf(dpPicker.getYear()) + "-" +
                    String.valueOf(dpPicker.getMonth()) + "-" +
                    String.valueOf(dpPicker.getDayOfMonth());
            String comment = " ";
            if (!etComments.getText().toString().replace(" ", "").equals("")) {
                comment = etComments.getText().toString();
            }

            String grade = etGrade.getText().toString();

            if(isEditData){
                //Edit exist data
                lesson_dao.deleteLesson(lesson);
                showHomeFragment();
            }else {
                //Insert new data
                lesson_dao.addLesson(new Lesson(
                        Long.parseLong(user_id),
                        lesson_name,
                        comment,
                        grade,
                        10,
                        date
                ));
                showHomeFragment();
            }
        }
    }

    private void init(View view) {
        etName = (EditText) view.findViewById(R.id.etName);
        lesson_dao = new LessonDAO(getContext());

        //Initialize Date Picker
        dpPicker = (DatePicker) view.findViewById(R.id.dpPicker);

        etComments = (EditText) view.findViewById(R.id.etComments);
        etStuCount = (EditText) view.findViewById(R.id.etStuCount);
        etGrade = (EditText) view.findViewById(R.id.etGrade);
        btnAddLesson = (Button) view.findViewById(R.id.btnAddLesson);
        if(isEditData){btnAddLesson.setText("Delete");}
        else{btnAddLesson.setText("Add");}
    }

    public void showToastError(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

    public boolean validate(){
        if(etName.getText().toString().replace(" ","").equals("")){
            showToastError("Lesson name is required");
            return false;
        }
        if(etGrade.getText().toString().replace(" ","").equals("")){
            showToastError("Grade is required");
            return false;
        }
        return true;
    }

    private void showHomeFragment(){
        HomeFragment homeFragment = new HomeFragment();
        int month = dpPicker.getMonth();
        homeFragment.setMonth_change(true, month);
        homeFragment.setUserDetails(email, user_name, user_id);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frmMain,homeFragment);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    showHomeFragment();
                    return true;
                }
                return false;
            }
        });
    }
}
