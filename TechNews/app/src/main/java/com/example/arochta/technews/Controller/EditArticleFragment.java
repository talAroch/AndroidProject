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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.R;


public class EditArticleFragment extends Fragment {
    private static final String ARG_PARAM1 = "articleId";

    // TODO: Rename and change types of parameters
    private int articleId;

    Article article;

    EditText title;
    EditText content;

    Button saveBtn;
    Button deleteBtn;
    Button cancelBtn;

    private OnFragmentInteractionListener mListener;

    public EditArticleFragment() {
        // Required empty public constructor
    }


    public static EditArticleFragment newInstance(int param1) {
        EditArticleFragment fragment = new EditArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("tag","On Create");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            articleId = getArguments().getInt(ARG_PARAM1);
            article = Model.instace.getArticle(articleId);
        }else
        {
            Log.d("tag","getArguments is NULL");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("tag","On Create View");
        View contentView = inflater.inflate(R.layout.fragment_edit_article, container, false);

        title = (EditText) contentView.findViewById(R.id.edit_article_title);
        content = (EditText) contentView.findViewById(R.id.edit_article_content);

        saveBtn = (Button) contentView.findViewById(R.id.editSaveBtn);
        deleteBtn = (Button) contentView.findViewById(R.id.editDeleteBtn);
        cancelBtn = (Button) contentView.findViewById(R.id.editCancelBtn);

        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        title.setText(article.getTitle());
        content.setText(article.getContent());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(title.getText().toString().compareTo(article.getTitle()) != 0) {
                    if (Model.instace.isArticleTitleExist(title.getText().toString())) {
                        Toast toast = Toast.makeText(getActivity(), "this title is already in the system", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else {
                    Model.instace.deleteArticle(articleId);
                    article.setTitle(title.getText().toString());
                    article.setContent(content.getText().toString());
                    Model.instace.addArticle(article);
                    DialogFragment df = new ArticleSaveDialog();
                    df.show(getFragmentManager(),"tag");
                    onButtonPressed(article.getArticleID());
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Model.instace.deleteArticle(articleId);
                onButtonPressed(-1);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonPressed(article.getArticleID());
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteractionEdit(id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d("tag","onAttach");
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
            Log.d("tag","onAttach");
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteractionEdit(int articleID);
    }
}
