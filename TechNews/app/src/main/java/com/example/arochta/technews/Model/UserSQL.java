package com.example.arochta.technews.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tal on 01-Aug-17.
 */

public class UserSQL {
    static final String USER_TABLE = "users";
    static final String USER_ID = "userID";
    static final String USER_EMAIL = "email";
    static final String USER_NAME = "name";
    static final String USER_PASSWORD = "password";

    /*static List<Article> getAllArticles(SQLiteDatabase db) {
        Cursor cursor = db.query(USER_TABLE , null, null, null, null, null, null);
        List<Article> list = new LinkedList<Article>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_ID);
            int emailIndex = cursor.getColumnIndex(ARTICLE_TITLE);
            int nameIndex = cursor.getColumnIndex(ARTICLE_AUTHOR_ID);
            int passwordIndex = cursor.getColumnIndex(ARTICLE_DATE);

            do {
                Article article = new Article();
                article.setArticleID(cursor.getInt(idIndex));
                article.setTitle(cursor.getString(titleIndex));
                article.setDate(cursor.getString(dateIndex));
                article.setImg(cursor.getString(imageUriIndex));
                article.setContent(cursor.getString(contentIndex));
                //article.setAuthor();
                list.add(article);
            } while (cursor.moveToNext());
        }
        return list;
    }*/

    static void addUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserID());
        values.put(USER_EMAIL,user.getEmail());
        values.put(USER_NAME,user.getName());
        values.put(USER_PASSWORD,user.getPassword());

        db.insert(USER_TABLE, USER_ID, values);
    }

    static User getUser(SQLiteDatabase db, String userID) {
        User user = null;
        String colummns[] = new String[1];
        colummns[0] = userID;
        Cursor cursor = db.query(USER_TABLE, null, USER_ID+"="+userID, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_ID);
            int emailIndex = cursor.getColumnIndex(USER_EMAIL);
            int nameIndex = cursor.getColumnIndex(USER_NAME);
            int passwordIndex = cursor.getColumnIndex(USER_PASSWORD);

            user = new User();
            user.setUserID(cursor.getInt(idIndex));
            user.setEmail(cursor.getString(emailIndex));
            user.setName(cursor.getString(nameIndex));
            user.setPassword(cursor.getString(passwordIndex));
        }
        return user;
    }

    static User validateUser(SQLiteDatabase db, String email,String password) {
        User user = null;
        String newEmail = "";
        newEmail = email.replace("@","%@");
        Cursor cursor = db.query(USER_TABLE, null, USER_EMAIL+"="+newEmail+" AND "+USER_PASSWORD+"="+password, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_ID);
            int emailIndex = cursor.getColumnIndex(USER_EMAIL);
            int nameIndex = cursor.getColumnIndex(USER_NAME);
            int passwordIndex = cursor.getColumnIndex(USER_PASSWORD);

            user = new User();
            user.setUserID(cursor.getInt(idIndex));
            user.setEmail(cursor.getString(emailIndex));
            user.setName(cursor.getString(nameIndex));
            user.setPassword(cursor.getString(passwordIndex));
            return user;
        }
        return user;
    }

    static public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE +
                " (" +
                USER_ID + " NUMBER PRIMARY KEY, " +
                USER_EMAIL + " TEXT, " +
                USER_NAME + " TEXT, " +
                USER_PASSWORD + " TEXT);");
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop " + USER_TABLE + ";");
        onCreate(db);
    }
}
