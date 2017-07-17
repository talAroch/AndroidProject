package com.example.arochta.technews.Model;

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
    private int id = 0;

    private Model(){
        /*for(int i=0;i<2;i++){
            User user = new User();
            user.setEmail("k" + i);
            user.setPassword("k" + i);
            users.add(user);
        }*/

        User user = new User();
        user.setEmail("a");
        user.setPassword("a");
        users.add(user);

        Article article = new Article();
        article.setTitle("a");
        article.setAuthor("a");
        article.setContent("a");
        articles.add(article);

        Article article2 = new Article();
        article2.setTitle("b");
        article2.setAuthor("b");
        article2.setContent("b");
        articles.add(article2);
    }

    public List<User> getAllUsers(){
        return users;
    }

    public List<Article> getAllArticles(){
        return  articles;
    }

    public void addUser(User user){users.add(user);}

    public void addArticle(Article article){
        articles.add(article);
    }

    public User getUser(String userEmail) {
        for (User user : users){
            if (user.getEmail().equals(userEmail)){
                return user;
            }
        }
        return null;
    }

    public Article getArticle(String title) {
        for (Article article : articles){
            if (article.getTitle().equals(title)){
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

    public boolean isArticleExist(String title){
        for (Article article : articles){
            if (article.getTitle().equals(title)){
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
            if (article.getTitle().equals(title)){
                index = users.indexOf(article);
            }
        }
        if(index<0) {
            return;
        }
        users.remove(index);
    }

    public boolean isUserInSystem(String userEmail,String userPassword){
        for (User s : users){
            if (s.getEmail().equals(userEmail) && s.getPassword().equals(userPassword)){
                return true;
            }
        }
        return false;
    }

}
