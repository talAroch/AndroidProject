package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.UserAuthentication;
import com.example.arochta.technews.R;

/**
 * This class handle the login process.
 */
public class Login extends Activity {

    String userEmail;
    String userPassword;
    EditText email;
    EditText password;
    Button loginbtn;
    MyProgressBar progressBar;
    TextView registrationLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = new MyProgressBar(Login.this);
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("uemail");
        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPass);
        loginbtn = (Button) findViewById(R.id.loginBtn);
        registrationLink = (TextView) findViewById(R.id.registerLink);

        if(userEmail != null){
            email.setText(userEmail);
        }
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
                progressBar.setDialogMessage("logging in");
                if(isFieldEmpty(email)){
                    progressBar.dismissDialog();
                    registerToast("you have to put email");
                }
                else if(isFieldEmpty(password)){
                    progressBar.dismissDialog();
                    registerToast("you have to put password");
                }
                else {
                    userEmail = email.getText().toString();
                    userPassword = password.getText().toString();
                    Model.instace.isUserInSystem(userEmail, userPassword, new UserAuthentication.AccountCallBack() {
                        @Override
                        public void onComplete() {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("currentUser",email.getText().toString());
                            startActivity(intent);
                            progressBar.dismissDialog();
                            finish();
                        }

                        @Override
                        public void onFail() {
                            progressBar.dismissDialog();
                            registerToast("wrong email or password");
                        }
                    });

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
