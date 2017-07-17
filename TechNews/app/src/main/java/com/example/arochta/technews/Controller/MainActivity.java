package com.example.arochta.technews.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.arochta.technews.R;


public class MainActivity extends Activity implements ArticlesListFragment.OnFragmentInteractionListener,ArticleShowFragment.OnFragmentInteractionListener,NewArticleFragment.OnFragmentInteractionListener{

    ArticlesListFragment articleListFragment;
    ArticleShowFragment articleShowFragment;
    NewArticleFragment newArticleFragment;

    static int currentID = 0;

    private Menu our_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.add_btn:
                newArticleFragment = new NewArticleFragment();
                our_menu.getItem(0).setVisible(false);
                our_menu.getItem(1).setVisible(false);
                fragmentTransaction.replace(R.id.main_fragment_container, newArticleFragment);
                fragmentTransaction.commit();
                break;
            case R.id.disconnect_btn:
                /*our_menu.getItem(0).setVisible(false);
                our_menu.getItem(1).setVisible(false);
                studentEditFragment = StudentEditFragment.newInstance(currentID);
                fragmentTransaction.replace(R.id.main_fragment_container, studentEditFragment);
                fragmentTransaction.commit();*/
                break;

        }
        return true;
    }

    @Override
    public void onFragmentInteractionList(int id) {
        currentID = id;
        our_menu.getItem(0).setVisible(false);
        our_menu.getItem(1).setVisible(false);
        articleShowFragment = articleShowFragment.newInstance(id);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, articleShowFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteractionDetails(int id) {

    }

    @Override
    public void onFragmentInteractionNew(String str) {

    }

    @Override
    public void onBackPressed()
    {//super.onBackPressed();
        our_menu.getItem(0).setVisible(true);
        our_menu.getItem(1).setVisible(true);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, articleListFragment);
        fragmentTransaction.commit();
    }
}