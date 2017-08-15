package com.example.prasanna.tutionclass.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prasanna.tutionclass.Constants;
import com.example.prasanna.tutionclass.DAO.UserDAO;
import com.example.prasanna.tutionclass.Models.User;
import com.example.prasanna.tutionclass.PassHash;
import com.example.prasanna.tutionclass.R;

public class LoginActivity extends AppCompatActivity {
    private ImageButton btn_login;
    private TextView link_signup;
    private EditText input_email;
    private EditText input_password;
    private UserDAO user_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize components
        init();
        initAutoLogin();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            input_email.setText(extras.getString("email_address"));
            input_password.requestFocus();
        }

        btn_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn();
                    }
                }
        );

        link_signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);
                    }
                }
        );
    }

    private void initAutoLogin() {
        User user = user_dao.getLoginUser();
        if(user!=null){
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("user_email", user.getEmail());
            i.putExtra("user_name", user.getName());
            i.putExtra("user_id", String.valueOf(user.getId()));
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    private void signIn(){
        if(validate()){
            String email = input_email.getText().toString();
            String password = input_password.getText().toString();
            try {
                password = PassHash.SHA1(password);
            } catch (Exception e) {
                Constants.printLog("Error on encrypting password: " + e.toString());
                Toast.makeText(this,"Invalid password", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = user_dao.UserAuth(email,password);
            if(user==null){
                Toast.makeText(this,"Invalid login credentials", Toast.LENGTH_LONG).show();
                return;
            }
            user_dao.setLoginStatusFlag(true,email);
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("user_email", user.getEmail());
            i.putExtra("user_name", user.getName());
            i.putExtra("user_id", String.valueOf(user.getId()));
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    private boolean validate(){
        boolean con = true;
        if(input_email.getText().toString().replace(" ","").equals("")){
            con = false;
            Toast.makeText(this,"Email is required", Toast.LENGTH_SHORT).show();
        }else if(input_password.getText().toString().replace(" ","").equals("")){
            con = false;
            Toast.makeText(this,"Password is required", Toast.LENGTH_SHORT).show();
        }else{
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(input_email.getText().toString()).matches()){
                Toast.makeText(this,"Invalid email address", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return con;
    }

    private void init() {
        btn_login = (ImageButton)findViewById(R.id.btn_login);
        link_signup = (TextView)findViewById(R.id.link_signup);
        input_password = (EditText)findViewById(R.id.input_password);
        input_email = (EditText) findViewById(R.id.input_email);
        user_dao = new UserDAO(this);
    }
}
