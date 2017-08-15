package com.example.prasanna.tutionclass.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.DAO.UserDAO;
import com.example.prasanna.tutionclass.Fragment.FeedbackFragment;
import com.example.prasanna.tutionclass.Fragment.HomeFragment;
import com.example.prasanna.tutionclass.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView tvEmail;
    private TextView tvUsername;
    private String email;
    private String user_name;
    private String user_id;
    private NavigationView navigationView;
    private UserDAO user_dao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initialize Each components
        init();
        showHomeFragment();
    }

    private void init() {
        View header = navigationView.getHeaderView(0);
        tvEmail = (TextView) header.findViewById(R.id.tvEmail);
        tvUsername = (TextView) header.findViewById(R.id.tvUsername);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            email = extras.getString("user_email");
            user_name = extras.getString("user_name");
            user_id = extras.getString("user_id");
            tvEmail.setText(email);
            tvUsername.setText(user_name);
        }
        user_dao = new UserDAO(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_home){
            showHomeFragment();
            toolbar.setTitle("Home");
        }else if(id==R.id.nav_feedback){
            showFeedbackFragment();
            toolbar.setTitle("Feedback");
        }else if(id==R.id.nav_about){

        }else if(id == R.id.nav_signout) {
            user_dao.setLoginStatusFlag(false, email);
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showHomeFragment(){
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setUserDetails(email, user_name, user_id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frmMain, homeFragment);
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        toolbar.setTitle("Home");
        transaction.commit();
    }

    public void showFeedbackFragment(){
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frmMain, feedbackFragment);
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        toolbar.setTitle("Feedback");
        transaction.commit();
    }
}
