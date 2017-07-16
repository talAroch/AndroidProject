package com.example.arochta.technews.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.arochta.technews.R;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;

    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPass);
        register = (Button) findViewById(R.id.loginBtn);
    }
}
