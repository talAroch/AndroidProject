package com.example.arochta.technews.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * this class contains and manage the articl data fields
 * Created by arochta on 17/07/2017.
 */

public class Article {

    private int articleID;
    private String title;
    private String author;
    private SimpleDateFormat sdf;
    private String date;
    private String imgURI;
    private String content;
    private boolean wasDeleted;//logical deletion
    private double lastUpdateDate;

    public Article(){
        articleID = 0;
        title = "";
        author = null;
        sdf = new SimpleDateFormat("dd/M/yyyy");
        date = sdf.format(new Date());
        imgURI = "";
        content = "";
        wasDeleted = false;
    }

    public double getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(double lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isWasDeleted() {
        return wasDeleted;
    }

    public void setWasDeleted(boolean wasDeleted) {
        this.wasDeleted = wasDeleted;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return imgURI;
    }

    public void setImg(String img) {
        this.imgURI = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String str = "articleId: " +articleID +". \n"+
                "title: " +title +". \n"+
                "author: " +author +". \n"+
                "date: " +date +". \n"+
                "image: " +imgURI +". \n"+
                "content: " +content +". \n";
        return str;
    }
}
