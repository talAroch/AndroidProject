package com.example.arochta.technews.Controller;

import android.app.Activity;
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
import com.example.arochta.technews.R;

public class NewArticleFragment extends Fragment {
    private static final String ARG_PARAM1 = "ArticleID";//CHANGE TO ARTICLE ID

    private int articleID;//CHANGE TO ARTICLE ID

    Article article;

    EditText title;
    EditText author;
    EditText content;

    Button saveBtn;
    Button cancelBtn;

    private OnFragmentInteractionListener mListener;

    public NewArticleFragment() {
        // Required empty public constructor
    }

    public static NewArticleFragment newInstance(int id) {
        NewArticleFragment fragment = new NewArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);//return the article that was created
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag","on create");
        if (getArguments() != null) {
            articleID = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_new_article, container, false);

        title = (EditText) contentView.findViewById(R.id.new_article_title);
        author= (EditText) contentView.findViewById(R.id.new_article_author);
        content= (EditText) contentView.findViewById(R.id.new_article_content);

        Button saveBtn = (Button) contentView.findViewById(R.id.newSaveBtn);
        Button cancelBtn = (Button) contentView.findViewById(R.id.newCancelBtn);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(Model.instace.isExist(idEt.getText().toString())){
                    Toast toast = Toast.makeText(getActivity(), "Student id is already in the system", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Article article = new Article();
                    article.setTitle(title.getText().toString());
                    article.setAuthor(author.getText().toString());
                    article.setContent(content.getText().toString());
                    Model.instace.addArticle(article);
                    //DialogFragment df = new SaveDialog();
                    //df.show(getFragmentManager(),"tag");
                    onButtonPressed("save");
                }*/
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("cancel");


            }
        });

        return contentView;
    }

    public void onBackPressed() {
        getActivity().getFragmentManager().popBackStack();
    }

    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteractionNew(id);
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

    public void  passData(int id){
        mListener.onFragmentInteractionNew(id);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteractionNew(int id);
    }

}
