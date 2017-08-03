package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.UserAuthentication;
import com.example.arochta.technews.R;

public class Registration extends Activity {

    String userEmail;
    String userPassword;

    EditText email;
    EditText password;
    EditText verifypass;

    Button registerbtn;

    MyProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar = new MyProgressBar(Registration.this);

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
                progressBar.setDialogMessage("registering");
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if(isFieldEmpty(userEmail)) {
                    progressBar.dismissDialog();
                    registerToast("you have to put email");
                }
                else if(isFieldEmpty(userPassword)){
                    progressBar.dismissDialog();
                    registerToast("you have to put password");
                }
                else if(isEmail(userEmail)){
                    progressBar.dismissDialog();
                    registerToast("not valid email");
                }
                else if(isPassStrong(userPassword)){
                    progressBar.dismissDialog();
                    registerToast("weak password,\n put at least 6 characters");
                }
                else if(verifyPassword()) {
                    userEmail = email.getText().toString();
                    userPassword = password.getText().toString();
                    Model.instace.addUser(userEmail,userPassword, new UserAuthentication.AccountCallBack() {
                        @Override
                        public void onComplete() {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.putExtra("uemail",email.getText().toString());
                            startActivity(intent);
                            progressBar.dismissDialog();
                            registerToast("registration was successfull");
                            finish();
                        }

                        @Override
                        public void onFail() {
                            progressBar.dismissDialog();
                            registerToast("registration failed");
                        }
                    });

                }
                else{
                    progressBar.dismissDialog();
                    registerToast("password don't match");
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

    public boolean isEmail(String email){
        int index1 = email.indexOf("@");
        if(index1 >= 0){
            email = email.substring(index1,email.length());
            int index2 = email.indexOf(".");
            if(index2 >=0 )
                return true;
        }
        return false;
    }

    public boolean isPassStrong(String password){
        return (password.length() > 5);
    }

    public boolean verifyPassword(){
        if(password.getText().toString().compareTo(verifypass.getText().toString()) == 0)
            return true;
        return false;
    }

    public void registerToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    public boolean isFieldEmpty(String txt){
        if(txt.compareTo("") == 0)
            return true;
        return false;
    }
}
