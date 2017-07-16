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

import com.example.arochta.technews.R;

public class NewArticleFragment extends Fragment {
    private static final String ARG_PARAM1 = "StudentId";//CHANGE TO ARTICLE ID

    private String StId;//CHANGE TO ARTICLE ID

    EditText title;
    EditText author;
    EditText content;

    Button saveBtn;
    Button cancelBtn;

    private OnFragmentInteractionListener mListener;

    public NewArticleFragment() {
        // Required empty public constructor
    }

    public static NewArticleFragment newInstance(String param1) {
        NewArticleFragment fragment = new NewArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);//return the article that was created
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag","on create");
        if (getArguments() != null) {
            StId = getArguments().getString(ARG_PARAM1);
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
                    Student student = new Student();
                    student.name = nameEt.getText().toString();
                    student.id = idEt.getText().toString();
                    student.phone = phone.getText().toString();
                    student.address = address.getText().toString();
                    student.checked = cb.isChecked();
                    student.myTime = myTime.getText().toString();
                    student.myDate = myDate.getText().toString();
                    Model.instace.addStudent(student);
                    DialogFragment df = new SaveDialog();
                    df.show(getFragmentManager(),"tag");
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

    public void onButtonPressed(String str) {
        if (mListener != null) {
            mListener.onFragmentInteractionNewSt(str);
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

    public void  passData(String str){
        mListener.onFragmentInteractionNewSt(str);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteractionNewSt(String str);
    }

}
