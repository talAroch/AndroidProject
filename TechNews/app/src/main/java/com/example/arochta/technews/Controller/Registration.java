package com.example.arochta.technews.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arochta.technews.R;

public class Registration extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText verifypass;

    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = (EditText)findViewById(R.id.registerEmail);
        password = (EditText)findViewById(R.id.registerPassword);
        verifypass = (EditText)findViewById(R.id.registerVerifypass);
        register = (Button) findViewById(R.id.registerBtn);
    }
}
