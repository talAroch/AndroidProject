package com.example.arochta.technews.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import static com.example.arochta.technews.Model.ArticleSQL.*;

/**
 * Created by arochta on 02/08/2017.
 */

public class ArticleFirebase {

    public void addArticle(Article article){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ARTICLE_TABLE);
        myRef.child(article.getArticleID()+"").setValue(article);
    }

    public void editArticle(Article article){
        addArticle(article);
    }

    public void removeArticle(Article article){
        article.setWasDeleted(true);
        addArticle(article);
    }

    interface GetArticleCallback {
        void onComplete(Article article);
        void onCancel();
    }

    public void getArticle(int articleID, final GetArticleCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ARTICLE_TABLE);
        myRef.child(articleID+"").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Article article = dataSnapshot.getValue(Article.class);
                callback.onComplete(article);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    interface GetAllArticlesAndObserveCallback {
        void onComplete(List<Article> list);
        void onCancel();
    }
    public void GetAllArticlesAndObserve(final GetAllArticlesAndObserveCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ARTICLE_TABLE);
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Article> list = new LinkedList<Article>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Article article = snap.getValue(Article.class);
                    if(!article.isWasDeleted())
                        list.add(article);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }


}
