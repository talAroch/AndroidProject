package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arochta.technews.Controller.MainActivity;

import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.User;
import com.example.arochta.technews.R;

public class Login extends Activity {

    EditText email;
    EditText password;

    Button loginbtn;

    TextView registrationLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPass);

        loginbtn = (Button) findViewById(R.id.loginBtn);

        registrationLink = (TextView) findViewById(R.id.registerLink);
    }

    @Override
    protected void onStart() {
        super.onStart();

        registrationLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Registration.class);
                startActivity(intent);
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(isFieldEmpty(email))
                    registerToast("you have to put email");
                else if(isFieldEmpty(password))
                    registerToast("you have to put password");
                else {
                    if(Model.instace.isUserInSystem(email.getText().toString(),password.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        registerToast("wrong email or password");
                    }

                }
            }
        });
    }

    public void registerToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    public boolean isFieldEmpty(EditText txt){
        if(txt.getText().toString().compareTo("") == 0)
            return true;
        return false;
    }

}
