package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.R;


/**
 * This class handle the viewing of an article.
 */
public class ArticleShowFragment extends Fragment {

    private static final String ARG_PARAM1 = "ArticleID";
    private int articleID;
    ImageView articleImg;
    TextView title;
    TextView author;
    TextView date;
    TextView content;
    Article article;
    MyProgressBar progressBar = MainActivity.getProgressBar();

    /**
     * Open a new show fragment on the specific article
     * @param id atricle id that pressed
     */
    public static ArticleShowFragment newInstance(int id) {
        ArticleShowFragment fragment = new ArticleShowFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar.setDialogMessage("loading");
        if (getArguments() != null) {
            articleID = getArguments().getInt(ARG_PARAM1);
            article = Model.instace.getArticle(articleID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.show_article, container, false);
        articleImg = (ImageView) contentView.findViewById(R.id.show_img);
        title = (TextView) contentView.findViewById(R.id.show_article_title);
        author = (TextView) contentView.findViewById(R.id.show_article_author);
        date = (TextView) contentView.findViewById(R.id.show_article_date);
        content = (TextView) contentView.findViewById(R.id.show_article_content);
        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        title.setText(article.getTitle());
        author.setText(article.getAuthor());
        date.setText(article.getDate());
        content.setText(article.getContent());
        Model.instace.getImage(article.getImg(), new Model.GetImageListener() {
            @Override
            public void onSuccess(Bitmap image) {
                articleImg.setImageBitmap(image);
            }

            @Override
            public void onFail() {
                Log.d("showImg","fail");
            }
        });
        progressBar.dismissDialog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
