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
    private Image img;
    private String content;

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

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
