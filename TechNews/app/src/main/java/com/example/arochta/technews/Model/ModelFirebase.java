package com.example.arochta.technews.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by arochta on 02/08/2017.
 */

public class ModelFirebase {
    public UserAuthentication userAuthentication;
    public ArticleFirebase articleFirebase;

    public ModelFirebase(){
        userAuthentication = new UserAuthentication();
    }
}
