package com.example.arochta.technews.Model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.arochta.technews.MyApplication;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by arochta on 02/08/2017.
 */

public class UserAuthentication {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public UserAuthentication() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public interface AccountCallBack {
        void onComplete();

        void onFail();
    }

    public void createAccount(String email, String password, final AccountCallBack callBack) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("auth", "createUserWithEmail:success");
                            callBack.onComplete();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("auth", "createUserWithEmail:failure", task.getException());
                            callBack.onFail();
                        }
                    }
                });
    }


    public void signin(String email, String password, final AccountCallBack callBack) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete (@NonNull Task < AuthResult > task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth", "signInWithEmail:success");
                    currentUser = mAuth.getCurrentUser();
                    callBack.onComplete();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth", "signInWithEmail:failure", task.getException());
                    callBack.onFail();
                }

                // ...
            }
        });
    }

    public void updateUserProfile(String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("auth", "User profile updated.");
                        }
                    }
                });
    }

    void signOut(){
        mAuth.signOut();
        currentUser = null;
    }
}
