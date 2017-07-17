package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.R;


public class ArticleShowFragment extends Fragment {
    private static final String ARG_PARAM1 = "ArticleID";

    // TODO: Rename and change types of parameters
    private int articleID;

    ImageView articleImg;

    TextView title;
    TextView author;
    TextView content;

    Article article;


    private OnFragmentInteractionListener mListener;

    public ArticleShowFragment() {

    }

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
        content = (TextView) contentView.findViewById(R.id.show_article_content);

        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //TODO: set image
        title.setText(article.getTitle());
        author.setText(article.getAuthor().getName());
        content.setText(article.getContent());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteractionDetails(id);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionDetails(int id);
    }
}