package com.example.arochta.technews.Model;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.Model.User;

/**
 * Created by arochta on 17/07/2017.
 */

public class Model {

    public final static Model instace = new Model();

    private List<User> users = new LinkedList<User>();
    private List<Article> articles = new LinkedList<Article>();
    private static int id = 1;

    private Model(){
        /*for(int i=0;i<2;i++){
            User user = new User();
            user.setEmail("k" + i);
            user.setPassword("k" + i);
            users.add(user);
        }*/

        User user = new User();
        user.setName("tal");
        user.setEmail("tal@tal.com");
        user.setPassword("tal");
        users.add(user);

        for (int i = 1; i < 6; i++) {
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
        }

    }

    public List<User> getAllUsers(){
        return users;
    }

    public List<Article> getAllArticles(){
        /*Log.d("model","start");
        for (int i = 0; i < articles.size(); i++) {
            Log.d("model",articles.get(i).getTitle());
        }
        Log.d("model","end");*/

        return  articles;
    }

    public void addUser(User user){users.add(user);}

    public void addArticle(Article article){
        article.setArticleID(getHighestArticleID()+1);
        articles.add(article);
        id++;
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

    public Article getArticle(int id) {
        for (Article article : articles){
            if (article.getArticleID() == id){
                return article;
            }
        }
        return null;
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



    public boolean isUserInSystem(String userEmail,String userPassword){
        for (User s : users){
            if (s.getEmail().equals(userEmail) && s.getPassword().equals(userPassword)){
                return true;
            }
        }
        return false;
    }

    public boolean isUserInSystem(User user){
        for (User s : users){
            if (s.getEmail().equals(user.getEmail()) && s.getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

}
