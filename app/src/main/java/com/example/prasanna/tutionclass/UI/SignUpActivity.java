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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {
    private TextView link_login;
    private ImageButton btn_signup;
    private EditText input_name;
    private EditText input_email;
    private EditText input_password;
    private EditText input_repassword;
    private UserDAO user_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initialize components
        init();

        btn_signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createUser();
                    }
                }
        );

        link_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoginPage("");
                    }
                }
        );
    }

    private void showLoginPage(String email){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        if(!email.equals("")){
            i.putExtra("email_address", email);
        }
        startActivity(i);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    private boolean validate(){
        boolean con = true;
        if(input_name.getText().toString().replace(" ","").equals("")){
            con = false;
            Toast.makeText(this,"Name is required", Toast.LENGTH_SHORT).show();
        }else if(input_email.getText().toString().replace(" ","").equals("")){
            con = false;
            Toast.makeText(this,"Email is required", Toast.LENGTH_SHORT).show();
        }else if(input_password.getText().toString().replace(" ","").equals("")){
            con = false;
            Toast.makeText(this,"Password is required", Toast.LENGTH_SHORT).show();
        }else if(input_repassword.getText().toString().replace(" ","").equals("")){
            con = false;
            Toast.makeText(this,"Retype your password", Toast.LENGTH_SHORT).show();
        }else{
            String password = input_password.getText().toString().replace(" ","");
            String repassword = input_repassword.getText().toString().replace(" ","");
            if(!password.equals(repassword)){
                Toast.makeText(this,"Password is not same", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (user_dao.isUserExist(input_email.getText().toString())) {
                Toast.makeText(this,"Email already taken", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(input_email.getText().toString()).matches()){
                Toast.makeText(this,"Invalid email address", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return con;
    }

    private void createUser() {
        if(validate()) {
            String encryptPassword = input_password.getText().toString().replace(" ","");
            try {
                encryptPassword = PassHash.SHA1(encryptPassword);
            } catch (Exception e) {
                Constants.printLog("Error on encrypting password: " + e.toString());
                Toast.makeText(this,"Invalid password", Toast.LENGTH_SHORT).show();
                return;
            }
            String email = input_email.getText().toString().replace(" ","");
            User user = new User(
                    input_name.getText().toString().replace(" ",""),
                    email,
                    encryptPassword
            );
            user_dao.addUser(user);
            showLoginPage(email);
        }
    }

    private void init() {
        link_login = (TextView)findViewById(R.id.link_login);
        btn_signup = (ImageButton)findViewById(R.id.btn_signup);
        input_name = (EditText)findViewById(R.id.input_name);
        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);
        input_repassword = (EditText)findViewById(R.id.input_repassword);
        user_dao = new UserDAO(this);
    }
}
