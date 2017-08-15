package com.example.prasanna.tutionclass.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.prasanna.tutionclass.DAO.FeedbackDAO;
import com.example.prasanna.tutionclass.Models.Feedback;
import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import java.util.ArrayList;

import static java.lang.Math.round;

/**
 * Created by prasanna_d on 8/15/2017.
 */

public class FeedbackViewFragment extends Fragment {
    private TextView tvTotalVotes;
    private TextView tvHappyStatus;
    private TextView tvNutralStatus;
    private TextView tvBadStatus;
    private Button btnReset;
    private Button btnStart;
    private Button btnViewGraph;
    private TextView tvFedName;
    private TextView tvFedDate;
    private TextView tvFedComments;
    private TextView tvFedNumDates;
    private TextView tvFedGrade;
    private TextView tvBadPercentage;
    private TextView tvNutralPercentage;
    private TextView tvHappyPercentage;

    private String email;
    private String user_name;
    private String user_id;
    private Lesson lesson;

    private FeedbackDAO feedback_dao;

    public void setUserDetails(String email, String user_name, String user_id) {
        this.email = email;
        this.user_name = user_name;
        this.user_id = user_id;
    }

    public void setLesson(Lesson lesson){
        this.lesson = lesson;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_view, container, false);
        //Initialize Variables;
        init(view);

        //Set Lesson data values
        tvFedName.setText("Name: " + lesson.getName());
        tvFedDate.setText("Date: " + lesson.getDate());
        if(lesson.getComments().equals("")){
            tvFedComments.setVisibility(View.GONE);
        }else {
            tvFedComments.setText("Comments: " + lesson.getComments());
            tvFedComments.setVisibility(View.VISIBLE);
        }
        tvFedNumDates.setText("Number of Students: " + String.valueOf(lesson.getStudent_count()));
        tvFedGrade.setText("Grade: " + lesson.getGrade());

        //Graphical View for later implementation
        btnViewGraph.setVisibility(View.GONE);
        btnStart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startVote();
                    }
                }
        );
        btnReset.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reset();
                    }
                }
        );

        ArrayList<Feedback> arrFeedback = feedback_dao.getFeedbackArray(lesson);
        int happy=0, neutral=0, sad=0;
        if(!arrFeedback.isEmpty()){
            for(int i=0; i<arrFeedback.size(); i++){
                Feedback feedback = arrFeedback.get(i);
                switch (feedback.getFlag()) {
                    case "1":
                        happy++;break;
                    case "2":
                        neutral++;break;
                    case "3":
                        sad++;break;
                }
            }
        }
        int total = arrFeedback.size();
        tvTotalVotes.setText("Total Votes: " + total);
        tvHappyStatus.setText(happy + "/" + total);
        tvNutralStatus.setText(neutral + "/" + total);
        tvBadStatus.setText(sad + "/" + total);

        tvNutralPercentage.setText("0%");
        tvHappyPercentage.setText("0%");
        tvBadPercentage.setText("0%");

        double neutralValue = (neutral/(double)total)*100;
        double happyValue = (happy/(double)total)*100;
        double sadValue = (sad/(double)total)*100;

        if(!Double.isNaN(neutralValue)) {
            tvNutralPercentage.setText(
                    String.format("%.0f", neutralValue) + "%"
            );
        }
        if(!Double.isNaN(happyValue)) {
            tvHappyPercentage.setText(
                    String.format("%.0f", happyValue) + "%"
            );
        }
        if(!Double.isNaN(sadValue)) {
            tvBadPercentage.setText(
                    String.format("%.0f", sadValue) + "%"
            );
        }

        return view;
    }

    private void reset() {
        feedback_dao.deleteFeedback(lesson);
        tvTotalVotes.setText("Total Votes: 0");
        tvHappyStatus.setText("0/0");
        tvNutralStatus.setText("0/0");
        tvBadStatus.setText("0/0");
        tvNutralPercentage.setText("0%");
        tvHappyPercentage.setText("0%");
        tvBadPercentage.setText("0%");
    }

    private void startVote() {
        FeedbackShowFragment feedbackShowFragment = new FeedbackShowFragment();
        feedbackShowFragment.setUserDetails(email, user_name, user_id);
        feedbackShowFragment.setLesson(lesson);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frmMain, feedbackShowFragment);
        transaction.commit();
    }

    private void init(View view) {
        tvTotalVotes = (TextView)view.findViewById(R.id.tvTotalVotes);
        tvHappyStatus= (TextView)view.findViewById(R.id.tvHappyStatus);
        tvNutralStatus= (TextView)view.findViewById(R.id.tvNutralStatus);
        tvBadStatus= (TextView)view.findViewById(R.id.tvBadStatus);
        btnReset= (Button) view.findViewById(R.id.btnReset);
        btnStart= (Button) view.findViewById(R.id.btnStart);
        btnViewGraph= (Button) view.findViewById(R.id.btnViewGraph);
        tvFedName= (TextView)view.findViewById(R.id.tvFedName);
        tvFedDate= (TextView)view.findViewById(R.id.tvFedDate);
        tvFedComments= (TextView)view.findViewById(R.id.tvFedComments);
        tvFedNumDates= (TextView)view.findViewById(R.id.tvFedNumDates);
        tvFedGrade= (TextView)view.findViewById(R.id.tvFedGrade);
        feedback_dao = new FeedbackDAO(getContext());
        tvBadPercentage = (TextView)view.findViewById(R.id.tvBadPercentage);
        tvHappyPercentage = (TextView)view.findViewById(R.id.tvHappyPercentage);
        tvNutralPercentage = (TextView)view.findViewById(R.id.tvNutralPercentage);
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

    private void showHomeFragment(){
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        feedbackFragment.setUserDetails(email, user_name, user_id);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frmMain,feedbackFragment);
        transaction.commit();
    }
}
