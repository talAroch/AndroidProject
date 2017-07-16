package com.example.arochta.technews.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arochta.technews.R;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;

    Button register;

    TextView registrationLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPass);

        register = (Button) findViewById(R.id.loginBtn);

        registrationLink = (TextView) findViewById(R.id.registerLink);
    }

    @Override
    protected void onStart() {
        super.onStart();

        registrationLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Registration.class);
                startActivity(intent);
            }
        });
    }

}
