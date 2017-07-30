package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.User;
import com.example.arochta.technews.R;

public class NewArticleFragment extends Fragment {
    private static final String ARG_PARAM1 = "ArticleID";//CHANGE TO ARTICLE ID

    private int articleID;//CHANGE TO ARTICLE ID

    User author;

    Article newArticle;

    EditText title;
    //EditText author;
    EditText content;

    Button saveBtn;
    Button cancelBtn;

    private OnFragmentInteractionListener mListener;

    public NewArticleFragment() {
        // Required empty public constructor
    }

    public static NewArticleFragment newInstance(User user) {
        NewArticleFragment fragment = new NewArticleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag","on create");
        if (getArguments() != null) {
            author = (User)getArguments().getSerializable(ARG_PARAM1);
        }

        newArticle = new Article();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_new_article, container, false);

        title = (EditText) contentView.findViewById(R.id.new_article_title);
        //author= (EditText) contentView.findViewById(R.id.new_article_author);
        content= (EditText) contentView.findViewById(R.id.new_article_content);

        Button saveBtn = (Button) contentView.findViewById(R.id.newSaveBtn);
        Button cancelBtn = (Button) contentView.findViewById(R.id.newCancelBtn);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Model.instace.isArticleTitleExist(title.getText().toString())){
                    Toast toast = Toast.makeText(getActivity(), "this title is already in the system", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    newArticle.setTitle(title.getText().toString());
                    newArticle.setAuthor(author);
                    newArticle.setContent(content.getText().toString());
                    Model.instace.addArticle(newArticle);
                    DialogFragment df = new ArticleSaveDialog();
                    df.show(getFragmentManager(),"tag");
                    onButtonPressed();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();


            }
        });

        return contentView;
    }

    public void onBackPressed() {
        getActivity().getFragmentManager().popBackStack();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteractionNew();
        }
    }

    @Override
    public void onAttach(Activity activity) { // for lower API's
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void  passData(String op){
        mListener.onFragmentInteractionNew();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteractionNew();
    }

}
