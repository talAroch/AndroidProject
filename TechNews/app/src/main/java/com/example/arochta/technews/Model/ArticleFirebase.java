package com.example.arochta.technews.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import static com.example.arochta.technews.Model.ArticleSQL.*;

/**
 * Created by arochta on 02/08/2017.
 */

public class ArticleFirebase {

    List<ChildEventListener> listeners = new LinkedList<ChildEventListener>();


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

    public interface GetArticleCallback {
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

    public interface GetAllArticlesAndObserveCallback {
        void onComplete(List<Article> list);
        void onCancel();
    }

    public void getAllArticlesAndObserve(final GetAllArticlesAndObserveCallback callback) {
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

    public void saveImage(Bitmap imageBmp, String name, final Model.SaveImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference imagesRef = storage.getReference().child("images").child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.fail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.complete(downloadUrl.toString());
            }
        });
    }

    public void getImage(String url, final Model.GetImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                listener.onSuccess(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("getImage",exception.getMessage());
                listener.onFail();
            }
        });
    }

    public interface RegisterArticlesUpdatesCallback{
        void onArticleUpdate(Article article);
    }

    public void registerArticlesUpdates(double lastUpdateDate, final RegisterArticlesUpdatesCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ARTICLE_TABLE);
        myRef.orderByChild("lastUpdateDate").startAt(lastUpdateDate);
        ChildEventListener listener = myRef.orderByChild("lastUpdateDate").startAt(lastUpdateDate)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d("TAG","onChildAdded called");
                        Article article = dataSnapshot.getValue(Article.class);
                        callback.onArticleUpdate(article);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Article article = dataSnapshot.getValue(Article.class);
                        callback.onArticleUpdate(article);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Article article = dataSnapshot.getValue(Article.class);
                        callback.onArticleUpdate(article);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Article article = dataSnapshot.getValue(Article.class);
                        callback.onArticleUpdate(article);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        listeners.add(listener);
    }

}
