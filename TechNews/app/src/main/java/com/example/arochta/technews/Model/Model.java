package com.example.arochta.technews.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.example.arochta.technews.Controller.MainActivity;
import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.MyApplication;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by arochta on 17/07/2017.
 */

public class Model {

    public final static Model instace = new Model();

    private List<Article> articles = new LinkedList<Article>();
    private static int id = 1;

    private ModelSQL modelSql;
    private ModelFirebase modelFirebase;

    private Model(){
        modelSql = new ModelSQL(MyApplication.getMyContext());
        modelFirebase = new ModelFirebase();

        synchArticlesDbAndregisterArticlesUpdates();
    }


    public void getAllArticles(final ArticleFirebase.GetAllArticlesAndObserveCallback callback){
        // read from local db
        List<Article> data = ArticleSQL.getAllArticles(modelSql.getReadableDatabase());

        //return list of students
        callback.onComplete(data);
    }

    public void addUser(String email,String password,final UserAuthentication.AccountCallBack callback){
        modelFirebase.userAuthentication.createAccount(email,password,new UserAuthentication.AccountCallBack(){
            @Override
            public void onComplete () {
                callback.onComplete();
            }

            @Override
            public void onFail () {
                callback.onFail();
            }
        });
    }

    public void addArticle(Article article){
        modelFirebase.articleFirebase.addArticle(article);
        //ArticleSQL.addArticle(modelSql.getReadableDatabase(),article);
    }


     //public void editArticle(Article article){
     //   Log.d("edit", "3");
     //   ArticleSQL.editArticle(modelSql.getReadableDatabase(),article);
    //}

    public int getHighestArticleID(){
        int max = 0;
        for (Article article : articles){
            if (article.getArticleID() > max){
                max = article.getArticleID();
            }
        }
        return max;
    }


    public String getCurrentUserEmail(){
        return modelFirebase.userAuthentication.getCurrentUser().getEmail();
    }

    public int generateID(){
        int number;
        do{
            Random rand = new Random();
            number = rand.nextInt(Integer.MAX_VALUE) + 1;

        }while(getArticle(number) != null);
        return number;
    }

    public Article getArticle(int id) {
        return ArticleSQL.getArticle(modelSql.getReadableDatabase(),id+"");

    }


    public boolean isArticleExist(int id){
        for (Article article : articles){
            if (article.getArticleID() == id){
                return true;
            }
        }
        return false;
    }

    public boolean isArticleTitleExist(String title){
        for (Article article : articles){
            if (article.getTitle().compareTo(title) == 0){
                return true;
            }
        }
        return false;
    }


    public void deleteArticle(Article article) {
        modelFirebase.articleFirebase.removeArticle(article);
    }



    public void isUserInSystem(String userEmail,String userPassword,final UserAuthentication.AccountCallBack callback){
        modelFirebase.userAuthentication.signin(userEmail,userPassword,new UserAuthentication.AccountCallBack(){
                        @Override
                        public void onComplete () {
                            callback.onComplete();
                        }

                        @Override
                        public void onFail () {
                            callback.onFail();
                        }
                });
    }

    public boolean isAdmin(){
        return (modelFirebase.userAuthentication.getCurrentUser().getEmail().compareTo("tal@tal.com") == 0);
    }

    public void signOut(){
        modelFirebase.userAuthentication.signOut();
    }

    public void editArticle(Article article){
        ArticleSQL.editArticle(modelSql.getReadableDatabase(),article);
        modelFirebase.articleFirebase.editArticle(article);
    }

    public boolean isAuthor(int articleID){
        Article article = getArticle(articleID);
        String currentEmail = getCurrentUserEmail();
        return (article.getAuthor().compareTo(currentEmail) == 0);
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    public void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        ModelFiles.saveImageToFile(imageBitmap,imageFileName);
    }

    public interface SaveImageListener {
        void complete(String url);
        void fail();
    }

    public void saveImage(final Bitmap imageBmp, final String fileName, final SaveImageListener listener) {
        if(imageBmp == null)
            Log.d("nulls","bitmap null");
        if(fileName == null)
            Log.d("nulls","filename null");
        modelFirebase.articleFirebase.saveImage(imageBmp, fileName, new SaveImageListener() {
            @Override
            public void complete(String url) {
                String file_name = URLUtil.guessFileName(url, null, null);
                saveImageToFile(imageBmp,file_name);
                listener.complete(url);
            }

            @Override
            public void fail() {
                listener.fail();
            }
        });


    }


    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }

    public void getImage(final String url, final GetImageListener listener) {
        //check if image exsist localy
        final String fileName = URLUtil.guessFileName(url, null, null);
        ModelFiles.loadImageFromFileAsynch(fileName, new ModelFiles.LoadImageFromFileAsynch() {
            @Override
            public void onComplete(Bitmap bitmap) {
                if (bitmap != null){
                    Log.d("TAG","getImage from local success " + fileName);
                    listener.onSuccess(bitmap);
                }else {
                    modelFirebase.articleFirebase.getImage(url, new GetImageListener() {
                        @Override
                        public void onSuccess(Bitmap image) {
                            String fileName = URLUtil.guessFileName(url, null, null);
                            Log.d("TAG","getImage from FB success " + fileName);
                            saveImageToFile(image,fileName);
                            listener.onSuccess(image);
                        }

                        @Override
                        public void onFail() {
                            Log.d("TAG","getImage from FB fail ");
                            listener.onFail();
                        }
                    });

                }
            }
        });
    }

    private void addPicureToGallery(File imageFile){
        //add the picture to the gallery so we dont need to manage the cache size
        Intent mediaScanIntent = new
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        MainActivity.getContextOfApplication().sendBroadcast(mediaScanIntent);
    }

    ////////////////
    /////////////////
    /// ///////////////


    public class UpdateArticleEvent {
        public final Article article;
        public UpdateArticleEvent(Article article) {
            this.article = article;
        }
    }



    private void synchArticlesDbAndregisterArticlesUpdates() {
        //1. get local lastUpdateTade
        SharedPreferences pref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        final double lastUpdateDate = pref.getFloat("ArticlesLastUpdateDate",0);
        Log.d("TAG","lastUpdateDate: " + lastUpdateDate);

        modelFirebase.articleFirebase.registerArticlesUpdates(lastUpdateDate,new ArticleFirebase.RegisterArticlesUpdatesCallback() {
            @Override
            public void onArticleUpdate(Article article) {
                //3. update the local db
                if(ArticleSQL.getArticle(modelSql.getReadableDatabase(),article.getArticleID()+"") != null)
                    ArticleSQL.editArticle(modelSql.getWritableDatabase(),article);
                else
                    ArticleSQL.addArticle(modelSql.getWritableDatabase(),article);
                //4. update the lastUpdateTade
                SharedPreferences pref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
                final double lastUpdateDate = pref.getFloat("ArticlesLastUpdateDate",0);
                if (lastUpdateDate < article.getLastUpdateDate()){
                    SharedPreferences.Editor prefEd = MyApplication.getMyContext().getSharedPreferences("TAG",
                            Context.MODE_PRIVATE).edit();
                    prefEd.putFloat("ArticlesLastUpdateDate", (float) article.getLastUpdateDate());
                    prefEd.commit();
                    Log.d("TAG","ArticlesLastUpdateDate: " + article.getLastUpdateDate());
                }

                EventBus.getDefault().post(new UpdateArticleEvent(article));
            }
        });
    }





}
