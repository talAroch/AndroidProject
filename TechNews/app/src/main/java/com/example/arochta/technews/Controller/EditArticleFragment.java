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

import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * This Class is fragment that handle The edit of an article.
 */
public class EditArticleFragment extends Fragment {
    private static final String ARG_PARAM1 = "articleId";

    private int articleId;
    private OnFragmentInteractionListener mListener;
    Article article;
    EditText title;
    EditText content;
    Bitmap imageBitmap;
    ImageView imageView;
    Context applicationContext = MainActivity.getContextOfApplication();
    MyProgressBar progressBar = MainActivity.getProgressBar();
    Button saveBtn;
    Button deleteBtn;
    Button cancelBtn;
    FragmentManager fm;

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
        progressBar.setDialogMessage("loading");
        fm = getFragmentManager();
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
        imageView = (ImageView) contentView.findViewById(R.id.edit_article_image);
        saveBtn = (Button) contentView.findViewById(R.id.editSaveBtn);
        deleteBtn = (Button) contentView.findViewById(R.id.editDeleteBtn);
        cancelBtn = (Button) contentView.findViewById(R.id.editCancelBtn);

        Model.instace.getImage(article.getImg(), new Model.GetImageListener() {
            @Override
            public void onSuccess(Bitmap image) {
                imageBitmap = image;
                imageView.setImageBitmap(imageBitmap);
            }

            @Override
            public void onFail() {
                Log.d("showImg","fail");
            }
        });

        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        title.setText(article.getTitle());
        Log.d("model", "load " + article.getImg());
        //imageView.setImageBitmap(imageBitmap);
        content.setText(article.getContent());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressBar.setDialogMessage("saving");
                article.setArticleID(articleId);
                article.setTitle(title.getText().toString());
                article.setContent(content.getText().toString());
                String fileName = articleId + ".jpeg";
                Model.instace.saveImage(imageBitmap, fileName, new Model.SaveImageListener() {
                @Override
                public void complete(String url) {
                    article.setImg(url);
                    Model.instace.editArticle(article);
                    progressBar.dismissDialog();
                    DialogFragment df = new ArticleSaveDialog();
                    df.show(fm,"tag");
                }

                @Override
                public void fail() {
                    progressBar.dismissDialog();
                    article.setImg("");
                }
                });
                onButtonPressed(article.getArticleID());
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressBar.setDialogMessage("removing");
                Model.instace.deleteArticle(article);
                progressBar.dismissDialog();
                onButtonPressed(-1);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonPressed(article.getArticleID());
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
                    Log.d("model", "before model");
                    getPicture(bitmap);
                    Log.d("model", "after model");
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
        //progressBar.setVisibility(GONE);
        imageBitmap = bitmap;
        if(imageBitmap != null)
            Log.d("model", "3");
            imageView.setImageBitmap(imageBitmap);
        Log.d("model", "4");
    }

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
