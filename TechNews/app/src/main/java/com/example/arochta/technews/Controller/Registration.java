package com.example.arochta.technews.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arochta.technews.R;

public class Registration extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText verifypass;

    Button registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = (EditText)findViewById(R.id.registerEmail);
        password = (EditText)findViewById(R.id.registerPassword);
        verifypass = (EditText)findViewById(R.id.registerVerifypass);

        registerbtn = (Button) findViewById(R.id.registerBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(isFieldEmpty(email))
                    registerToast("you have to put email");
                else if(isFieldEmpty(password))
                    registerToast("you have to put password");
                else if(verifyPassword()) {
                    //TODO: send to model
                    //email.getText();
                    //password.getText();
                }
                else{
                    registerToast("passwords don't match");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public boolean verifyPassword(){
        if(password.getText().toString().compareTo(verifypass.getText().toString()) == 0)
            return true;
        return false;
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
