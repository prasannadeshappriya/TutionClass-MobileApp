package com.example.prasanna.tutionclass.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.prasanna.tutionclass.Models.Lesson;
import com.example.prasanna.tutionclass.R;

import static com.example.prasanna.tutionclass.Constants.printLog;

/**
 * Created by prasanna_d on 8/15/2017.
 */

public class FeedbackShowFragment extends Fragment {
    private ImageButton btnHappyVote;
    private ImageButton btnNutralVote;
    private ImageButton btnSadVote;

    private String email;
    private String user_name;
    private String user_id;
    private Lesson lesson;

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
        View view = inflater.inflate(R.layout.fragment_feedback_show, container, false);
        //Initialize variables
        init(view);

        btnHappyVote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        happyVotePressed();
                    }
                }
        );

        btnNutralVote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nutralVotePressed();
                    }
                }
        );

        btnSadVote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sadVotePressed();
                    }
                }
        );
        return view;
    }

    private void sadVotePressed() {
        printLog("Sad");
    }

    private void nutralVotePressed() {
        printLog("Neutral");
    }

    private void happyVotePressed() {
        printLog("Happy");
    }

    private void init(View view) {
        btnHappyVote = (ImageButton) view.findViewById(R.id.btnHappyVote);
        btnNutralVote = (ImageButton) view.findViewById(R.id.btnNutralVote);
        btnSadVote = (ImageButton) view.findViewById(R.id.btnSadVote);
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
                    showPreviousFragment();
                    return true;
                }
                return false;
            }
        });
    }

    private void showPreviousFragment(){
        FeedbackViewFragment feedbackViewFragment = new FeedbackViewFragment();
        feedbackViewFragment.setUserDetails(email, user_name, user_id);
        feedbackViewFragment.setLesson(lesson);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frmMain, feedbackViewFragment);
        transaction.commit();
    }
}
