package com.example.arochta.technews.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tal on 01-Aug-17.
 */

public class ArticleSQL {
    static final String ARTICLE_TABLE = "articles";
    static final String ARTICLE_ID = "articleID";
    static final String ARTICLE_TITLE = "title";
    static final String ARTICLE_AUTHOR_ID = "authorID";
    static final String ARTICLE_DATE = "date";
    static final String ARTICLE_IMAGE_URI = "imageuri";
    static final String ARTICLE_CONTENT = "content";
    static final String ARTICLE_WAS_DELETED = "wasDeleted";

    static List<Article> getAllArticles(SQLiteDatabase db) {
        Cursor cursor = db.query(ARTICLE_TABLE , null, null, null, null, null, null);
        List<Article> list = new LinkedList<Article>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ARTICLE_ID);
            int titleIndex = cursor.getColumnIndex(ARTICLE_TITLE);
            int authorIndex = cursor.getColumnIndex(ARTICLE_AUTHOR_ID);
            int dateIndex = cursor.getColumnIndex(ARTICLE_DATE);
            int imageUriIndex = cursor.getColumnIndex(ARTICLE_IMAGE_URI);
            int contentIndex = cursor.getColumnIndex(ARTICLE_CONTENT);
            int wasDeletedIndex = cursor.getColumnIndex(ARTICLE_WAS_DELETED);

            do {

                Article article = new Article();
                article.setArticleID(cursor.getInt(idIndex));
                int authorid = cursor.getInt(authorIndex);
                User author = UserSQL.getUser(db,authorid+"");
                article.setAuthor(author);
                article.setTitle(cursor.getString(titleIndex));
                article.setDate(cursor.getString(dateIndex));
                article.setImg(cursor.getString(imageUriIndex));
                article.setContent(cursor.getString(contentIndex));
                article.setWasDeleted(cursor.getInt(wasDeletedIndex) == 1);

                //article.setAuthor();
                if (!article.isWasDeleted()){
                    list.add(article);
                    //Log.d("all", article.toString());
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    static void addArticle(SQLiteDatabase db, Article article) {
        ContentValues values = new ContentValues();
        values.put(ARTICLE_ID, article.getArticleID());
        values.put(ARTICLE_TITLE, article.getTitle());
        values.put(ARTICLE_AUTHOR_ID, article.getAuthor().getUserID());
        values.put(ARTICLE_DATE, article.getDate());
        values.put(ARTICLE_IMAGE_URI, article.getImg());
        values.put(ARTICLE_CONTENT, article.getContent());
        values.put(ARTICLE_WAS_DELETED, article.isWasDeleted() == true);
        Log.d("add", article.toString());
        db.insert(ARTICLE_TABLE, ARTICLE_ID, values);
    }

    static Article getArticle(SQLiteDatabase db, String articleID) {
        Article article = null;
        String colummns[] = new String[1];
        colummns[0] = ARTICLE_ID;
        Cursor cursor = db.query(ARTICLE_TABLE, null, ARTICLE_ID+"="+articleID, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ARTICLE_ID);
            int titleIndex = cursor.getColumnIndex(ARTICLE_TITLE);
            int authorIndex = cursor.getColumnIndex(ARTICLE_AUTHOR_ID);
            int dateIndex = cursor.getColumnIndex(ARTICLE_DATE);
            int imageUriIndex = cursor.getColumnIndex(ARTICLE_IMAGE_URI);
            int contentIndex = cursor.getColumnIndex(ARTICLE_CONTENT);
            int wasDeletedIndex = cursor.getColumnIndex(ARTICLE_WAS_DELETED);

            article = new Article();
            article.setArticleID(cursor.getInt(idIndex));
            article.setTitle(cursor.getString(titleIndex));
            article.setDate(cursor.getString(dateIndex));
            article.setImg(cursor.getString(imageUriIndex));
            article.setContent(cursor.getString(contentIndex));
            article.setWasDeleted(cursor.getInt(wasDeletedIndex) == 1);
            int authorid = cursor.getInt(authorIndex);
            User author = UserSQL.getUser(db,authorid+"");
            article.setAuthor(author);
        }
        return article;
    }

    static public void editArticle(SQLiteDatabase db, Article article){
        ContentValues values = new ContentValues();
        Log.d("edit", article.toString());
        values.put(ARTICLE_ID, article.getArticleID());
        values.put(ARTICLE_TITLE, article.getTitle());
        values.put(ARTICLE_AUTHOR_ID, article.getAuthor().getUserID());
        values.put(ARTICLE_DATE, article.getDate());
        values.put(ARTICLE_IMAGE_URI, article.getImg());
        values.put(ARTICLE_CONTENT, article.getContent());
        values.put(ARTICLE_WAS_DELETED, article.isWasDeleted() == true);
        db.update(ARTICLE_TABLE,values, ARTICLE_ID+"="+article.getArticleID(), null);
    }

    static public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ARTICLE_TABLE +
                " (" +
                ARTICLE_ID + " NUMBER PRIMARY KEY, " +
                ARTICLE_TITLE + " TEXT, " +
                ARTICLE_AUTHOR_ID + " NUMBER, " +
                ARTICLE_DATE + " TEXT, " +
                ARTICLE_IMAGE_URI + " TEXT, " +
                ARTICLE_CONTENT + " TEXT, " +
                ARTICLE_WAS_DELETED + " NUMBER);");
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop " + ARTICLE_TABLE + ";");
        onCreate(db);
    }
}
