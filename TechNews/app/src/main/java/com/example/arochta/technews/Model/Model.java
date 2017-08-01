package com.example.arochta.technews.Model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

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
import com.example.arochta.technews.Model.User;
import com.example.arochta.technews.MyApplication;

/**
 * Created by arochta on 17/07/2017.
 */

public class Model {

    public final static Model instace = new Model();

    private List<User> users = new LinkedList<User>();
    private List<Article> articles = new LinkedList<Article>();
    private static int id = 1;

    private ModelSQL modelSql;

    private Model(){
        User user = new User();
        user.setName("tal");
        user.setEmail("tal@tal.com");
        user.setPassword("tal");
        users.add(user);
        modelSql = new ModelSQL(MyApplication.getMyContext());

        /*for (int i = 1; i < 6; i++) {
            Article article = new Article();
            article.setTitle("test"+i);
            article.setAuthor(user);
            String content1 = "";
            for (int j = 0; j < 20; j++) {
                content1 = content1 + "test"+i;
            }
            article.setContent(content1);
            article.setArticleID(id);
            articles.add(article);

            id++;
        }*/

    }


    public List<Article> getAllArticles(){
        return  ArticleSQL.getAllArticles(modelSql.getReadableDatabase());
    }

    public void addUser(User user){
        UserSQL.addUser(modelSql.getReadableDatabase(),user);
    }

    public void addArticle(Article article){
        //articles.add(article);
        ArticleSQL.addArticle(modelSql.getReadableDatabase(),article);
        id++;
    }

    public void editArticle(Article article){
        Log.d("edit", "3");
        ArticleSQL.editArticle(modelSql.getReadableDatabase(),article);
    }

    public int getHighestArticleID(){
        int max = 0;
        for (Article article : articles){
            if (article.getArticleID() > max){
                max = article.getArticleID();
            }
        }
        return max;
    }

    public User getUser(String userEmail) {
        for (User user : users){
            if (user.getEmail().equals(userEmail)){
                return user;
            }
        }
        return null;

    }

    public int generateID(){
        Random rand = new Random();
        int  n = rand.nextInt(Integer.MAX_VALUE) + 1;
        return n;
    }

    public Article getArticle(int id) {
        return ArticleSQL.getArticle(modelSql.getReadableDatabase(),id+"");
    }

    public boolean isUserExist(String userEmail){
        for (User s : users){
            if (s.getEmail().equals(userEmail)){
                return true;
            }
        }
        return false;
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

    public void deleteUser(String userEmail) {
        int index = 0;
        for (User user : users){
            if (user.getEmail().equals(userEmail)){
                index = users.indexOf(user);
            }
        }
        if(index<0) {
            return;
        }
        users.remove(index);
    }

    public void deleteArticle(String title) {
        int index = 0;
        for (Article article : articles){
            if (article.getArticleID() == id){
                index = articles.indexOf(article);
            }
        }
        if(index<0) {
            return;
        }
        articles.remove(index);
    }

    public void deleteArticle(Article ar) {
        int index = 0;
        for (Article article : articles){
            if (article.equals(ar)){
                index = articles.indexOf(article);
                break;
            }
        }
        if(index<0) {
            return;
        }
        articles.remove(index);
    }

    public void deleteArticle(int id) {
        int index = 0;
        for (Article article : articles){
            if (article.getArticleID() == id){
                index = articles.indexOf(article);
            }
        }
        if(index<0) {
            return;
        }
        articles.remove(index);
    }



    public User isUserInSystem(String userEmail,String userPassword){
        User user = new User();
        user.setUserID(20);
        user.setName("idan");
        user.setEmail("idan@idan.com");
        user.setPassword("1");
        return user;
        //UserSQL.validateUser(modelSql.getReadableDatabase(),userEmail,userPassword);
    }

    public boolean isUserInSystem(User user){
        for (User s : users){
            if (s.getEmail().equals(user.getEmail()) && s.getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }

    public interface SaveImageListener {
        void complete(String url);
        void fail();
    }

    public void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputStream out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            //addPicureToGallery(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap loadImageFromFile(String imageFileName){
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void addPicureToGallery(File imageFile){
        //add the picture to the gallery so we dont need to manage the cache size
        Intent mediaScanIntent = new
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        MainActivity.getContextOfApplication().sendBroadcast(mediaScanIntent);
    }

}
