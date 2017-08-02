package com.example.arochta.technews.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tal on 01-Aug-17.
 */

public class ArticleSQL {
    public static final String ARTICLE_TABLE = "articles";
    public static final String ARTICLE_ID = "articleID";
    public static final String ARTICLE_TITLE = "title";
    public static final String ARTICLE_AUTHOR = "author";
    public static final String ARTICLE_DATE = "date";
    public static final String ARTICLE_IMAGE_URI = "imageuri";
    public static final String ARTICLE_CONTENT = "content";
    public static final String ARTICLE_WAS_DELETED = "wasDeleted";
    public static final String ARTICLE_LAST_UPDATE = "lastUpdate";

    static List<Article> getAllArticles(SQLiteDatabase db) {
        Cursor cursor = db.query(ARTICLE_TABLE , null, null, null, null, null, null);
        List<Article> list = new LinkedList<Article>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ARTICLE_ID);
            int titleIndex = cursor.getColumnIndex(ARTICLE_TITLE);
            int authorIndex = cursor.getColumnIndex(ARTICLE_AUTHOR);
            int dateIndex = cursor.getColumnIndex(ARTICLE_DATE);
            int imageUriIndex = cursor.getColumnIndex(ARTICLE_IMAGE_URI);
            int contentIndex = cursor.getColumnIndex(ARTICLE_CONTENT);
            int wasDeletedIndex = cursor.getColumnIndex(ARTICLE_WAS_DELETED);
            int lastUpdateIndex = cursor.getColumnIndex(ARTICLE_LAST_UPDATE);

            do {
                Article article = new Article();
                article.setArticleID(cursor.getInt(idIndex));
                article.setAuthor(cursor.getString(authorIndex));
                article.setTitle(cursor.getString(titleIndex));
                article.setDate(cursor.getString(dateIndex));
                article.setImg(cursor.getString(imageUriIndex));
                article.setContent(cursor.getString(contentIndex));
                article.setWasDeleted(cursor.getInt(wasDeletedIndex) == 1);
                article.setLastUpdateDate(cursor.getDouble(lastUpdateIndex));
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
        values.put(ARTICLE_AUTHOR, article.getAuthor());
        values.put(ARTICLE_DATE, article.getDate());
        values.put(ARTICLE_IMAGE_URI, article.getImg());
        values.put(ARTICLE_CONTENT, article.getContent());
        values.put(ARTICLE_LAST_UPDATE, article.getLastUpdateDate());
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
            int authorIndex = cursor.getColumnIndex(ARTICLE_AUTHOR);
            int dateIndex = cursor.getColumnIndex(ARTICLE_DATE);
            int imageUriIndex = cursor.getColumnIndex(ARTICLE_IMAGE_URI);
            int contentIndex = cursor.getColumnIndex(ARTICLE_CONTENT);
            int wasDeletedIndex = cursor.getColumnIndex(ARTICLE_WAS_DELETED);
            int lastUpdateIndex = cursor.getColumnIndex(ARTICLE_LAST_UPDATE);

            article = new Article();
            article.setArticleID(cursor.getInt(idIndex));
            article.setTitle(cursor.getString(titleIndex));
            article.setDate(cursor.getString(dateIndex));
            article.setImg(cursor.getString(imageUriIndex));
            article.setContent(cursor.getString(contentIndex));
            article.setWasDeleted(cursor.getInt(wasDeletedIndex) == 1);
            article.setAuthor(cursor.getString(authorIndex));
            article.setLastUpdateDate(cursor.getDouble(lastUpdateIndex));
        }
        return article;
    }

    static public void editArticle(SQLiteDatabase db, Article article){
        ContentValues values = new ContentValues();
        Log.d("edit", article.toString());
        values.put(ARTICLE_ID, article.getArticleID());
        values.put(ARTICLE_TITLE, article.getTitle());
        values.put(ARTICLE_AUTHOR, article.getAuthor());
        values.put(ARTICLE_DATE, article.getDate());
        values.put(ARTICLE_IMAGE_URI, article.getImg());
        values.put(ARTICLE_CONTENT, article.getContent());
        values.put(ARTICLE_LAST_UPDATE, article.getLastUpdateDate());
        values.put(ARTICLE_WAS_DELETED, article.isWasDeleted() == true);
        db.update(ARTICLE_TABLE,values, ARTICLE_ID+"="+article.getArticleID(), null);
    }

    //donot touch pen tipagatcj
    //its been a while - crocodile
    //drink pepsi be fucking sexy

    static public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ARTICLE_TABLE +
                " (" +
                ARTICLE_ID + " NUMBER PRIMARY KEY, " +
                ARTICLE_TITLE + " TEXT, " +
                ARTICLE_AUTHOR + " TEXT, " +
                ARTICLE_DATE + " TEXT, " +
                ARTICLE_IMAGE_URI + " TEXT, " +
                ARTICLE_CONTENT + " TEXT, " +
                ARTICLE_LAST_UPDATE + " NUMBER, " +
                ARTICLE_WAS_DELETED + " NUMBER);");
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop " + ARTICLE_TABLE + ";");
        onCreate(db);
    }
}
