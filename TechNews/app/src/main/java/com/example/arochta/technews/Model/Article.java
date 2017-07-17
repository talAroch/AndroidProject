package com.example.arochta.technews.Model;

import android.media.Image;

import java.util.Date;

/**
 * Created by arochta on 17/07/2017.
 */

public class Article {

    private int articleID;
    private String title;
    private String author;//CHANGE TO USER
    private Date date;
    private String imgURI;
    private String content;

    public Article(){}

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
