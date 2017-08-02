package com.example.arochta.technews.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.Model.Model;
import com.example.arochta.technews.R;


public class MainActivity extends Activity implements ArticlesListFragment.OnFragmentInteractionListener,ArticleShowFragment.OnFragmentInteractionListener,NewArticleFragment.OnFragmentInteractionListener,EditArticleFragment.OnFragmentInteractionListener{

    static String currentUser;
    static int currentArticleID = 0;

    ArticlesListFragment articleListFragment;
    ArticleShowFragment articleShowFragment;
    NewArticleFragment newArticleFragment;
    EditArticleFragment editArticleFragment;

    //MyProgressBar progressBar;
    //ProgressDialog progressBar;

    private Menu our_menu;
    //menu.getItem(0) - add article
    //menu.getItem(1) - disconnect
    //menu.getItem(2) - edit article

    static Context contextOfApplication;

    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contextOfApplication = getApplicationContext();

        /*progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setTitle("Loading");
        progressBar.setMessage("please wait...");
        progressBar.show();
        progressBar.dismiss();*/

        //progressBar = new MyProgressBar(getBaseContext());

        Intent intent = getIntent();

        currentUser = intent.getStringExtra("currentUser");
        setTitle("hello, "+ currentUser);

        setContentView(R.layout.activity_main);

        try{
        getActionBar().setDisplayHomeAsUpEnabled(false);
        }catch (NullPointerException e){
            Log.d("tag","null pointer exeption");
        }

        FragmentManager fm = getFragmentManager();

        articleListFragment = new ArticlesListFragment();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container, articleListFragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        our_menu = menu;
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setMenuIcons(true,true,false);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.menu_add_btn:
                setMenuIcons(false,false,false);
                newArticleFragment = NewArticleFragment.newInstance(currentUser);
                fragmentTransaction.replace(R.id.main_fragment_container, newArticleFragment);
                fragmentTransaction.commit();
                break;
            case R.id.menu_disconnect_btn:
                //progressBar.setDialogMessage("disconnecting");
                setMenuIcons(false,false,false);
                Model.instace.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                //progressBar.dismissDialog();
                finish();
                break;
            case R.id.menu_edit_btn:
                setMenuIcons(false,false,false);
                editArticleFragment = editArticleFragment.newInstance(currentArticleID);
                fragmentTransaction.replace(R.id.main_fragment_container, editArticleFragment);
                fragmentTransaction.commit();
                break;

        }
        return true;
    }

    @Override
    public void onFragmentInteractionList(int id) {
        currentArticleID = id;
        //verify the user;
        if(Model.instace.isAuthor(id))
            setMenuIcons(false,false,true);
        setMenuIcons(false,false,false);
        articleShowFragment = articleShowFragment.newInstance(id);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, articleShowFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteractionDetails(int id) {

    }

    @Override
    public void onFragmentInteractionNew() {
        setMenuIcons(true,true,false);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, articleListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteractionEdit(int articleID) {
        if(articleID == -1){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, articleListFragment);
            fragmentTransaction.commit();
        }
        else{
            articleShowFragment = articleShowFragment.newInstance(articleID);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, articleShowFragment);
            fragmentTransaction.commit();
        }
        setMenuIcons(true,true,false);
    }

    @Override
    public void onBackPressed()
    {//super.onBackPressed();
        setMenuIcons(true,true,false);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, articleListFragment);
        fragmentTransaction.commit();
    }

    public void setMenuIcons(boolean add,boolean disconnect,boolean edit){
        our_menu.getItem(0).setVisible(add);
        our_menu.getItem(1).setVisible(disconnect);
        our_menu.getItem(2).setVisible(edit);
    }

}