package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * This class is handle to write of a new article and it's publish
 */
public class NewArticleFragment extends Fragment {
    private static final String ARG_PARAM1 = "ArticleID";

    String author;
    Article newArticle;
    Bitmap imageBitmap;
    EditText title;
    EditText content;
    ImageView imageView;
    MyProgressBar progressBar = MainActivity.getProgressBar();
    Context applicationContext = MainActivity.getContextOfApplication();
    FragmentManager fm;

    private OnFragmentInteractionListener mListener;

    public static NewArticleFragment newInstance(String user) {
        NewArticleFragment fragment = new NewArticleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar.setDialogMessage("loading");
        fm = getFragmentManager();
        Log.d("tag","on create");
        if (getArguments() != null) {
            author = getArguments().getString(ARG_PARAM1);
        }

        newArticle = new Article();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_new_article, container, false);

        title = (EditText) contentView.findViewById(R.id.new_article_title);
        content= (EditText) contentView.findViewById(R.id.new_article_content);
        imageView = (ImageView) contentView.findViewById(R.id.new_article_image);

        Button saveBtn = (Button) contentView.findViewById(R.id.newSaveBtn);
        Button cancelBtn = (Button) contentView.findViewById(R.id.newCancelBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setDialogMessage("saving");
                newArticle.setArticleID(Model.instace.generateID());
                newArticle.setTitle(title.getText().toString());
                newArticle.setAuthor(author);
                newArticle.setContent(content.getText().toString());
                String fileName = (newArticle.getArticleID()) + ".jpeg";
                Log.d("new",fileName);
                if(imageBitmap != null)
                    Log.d("new", "is not null");
                if(imageBitmap != null && fileName != null) {
                    Model.instace.saveImage(imageBitmap, fileName, new Model.SaveImageListener() {
                        @Override
                        public void complete(String url) {
                            newArticle.setImg(url);
                            Model.instace.addArticle(newArticle);
                            progressBar.dismissDialog();
                            DialogFragment df = new ArticleSaveDialog();
                            df.show(fm, "tag");
                        }

                        @Override
                        public void fail() {
                            newArticle.setImg("");
                        }
                    });
                }
                onButtonPressed();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBitmap = null;
                dispatchTakePictureIntent();
            }
        });
        progressBar.dismissDialog();

        return contentView;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra
                (
                        Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[] { takePhotoIntent }
                );
        startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(data.getData() != null) {
                try {
                    InputStream inputStream = applicationContext.getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    getPicture(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Bundle extras = data.getExtras();
                getPicture((Bitmap) extras.get("data"));
            }
        }
        else
        {
            //stop the ProgressBar
            getPicture(null);
        }
    }

    public void getPicture(Bitmap bitmap){
        imageBitmap = bitmap;
        if(bitmap != null)
            imageView.setImageBitmap(bitmap);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteractionNew();
    }
}
