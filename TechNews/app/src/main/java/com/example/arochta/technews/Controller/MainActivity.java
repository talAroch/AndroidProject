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

public class MainActivity extends Activity implements NewArticleFragment.OnFragmentInteractionListener, ArticlesListFragment.OnFragmentInteractionListener{

    NewArticleFragment newStudentFragment;
    ArticlesListFragment studentListFragment;

    static String currentID = "";

    private Menu our_menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        FragmentManager fm = getFragmentManager();

        studentListFragment = new ArticlesListFragment();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container, studentListFragment);
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
        menu.getItem(1).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.add_btn:
                newStudentFragment = new NewArticleFragment();
                our_menu.getItem(0).setVisible(false);
                our_menu.getItem(1).setVisible(false);
                fragmentTransaction.replace(R.id.main_fragment_container, newStudentFragment);
                fragmentTransaction.commit();
                break;
            /*case R.id.edit_btn://CHANGE TO DISCONNECT
                our_menu.getItem(0).setVisible(false);
                our_menu.getItem(1).setVisible(false);
                studentEditFragment = StudentEditFragment.newInstance(currentID);
                fragmentTransaction.replace(R.id.main_fragment_container, studentEditFragment);
                fragmentTransaction.commit();
                break;*/

        }
        return true;
    }

    @Override
    public void onFragmentInteractionNewSt(String str) {
        our_menu.getItem(0).setVisible(true);
        our_menu.getItem(1).setVisible(false);

        if(str.compareTo("save") == 0){
            studentListFragment = new ArticlesListFragment();////////////////////////////////////////////////////
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, studentListFragment);
            fragmentTransaction.commit();

        }
        else if(str.compareTo("cancel") == 0){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, studentListFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onFragmentInteractionList(String str) {
        currentID = str;
        our_menu.getItem(0).setVisible(false);
        our_menu.getItem(1).setVisible(true);
        //TODO:create article details
        //studentDetailsFragment = StudentDetailsFragment.newInstance(str);
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.main_fragment_container, studentDetailsFragment);
        //fragmentTransaction.commit();
    }

    /*@Override
    public void onFragmentInteractionDetails(String str) {
        our_menu.getItem(0).setVisible(false);
        our_menu.getItem(1).setVisible(false);
        studentEditFragment = StudentEditFragment.newInstance(str);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, studentEditFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteractionEdit(String str,String id) {
        if(str.compareTo("save") == 0){
            our_menu.getItem(0).setVisible(false);
            our_menu.getItem(1).setVisible(true);
            studentDetailsFragment = StudentDetailsFragment.newInstance(id);;////////////////////////////////////////////////////
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, studentDetailsFragment);
            fragmentTransaction.commit();
        }
        else if(str.compareTo("delete") == 0){
            our_menu.getItem(0).setVisible(true);
            our_menu.getItem(1).setVisible(false);
            studentListFragment = new StudentListFragment();////////////////////////////////////////////////////
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, studentListFragment);
            fragmentTransaction.commit();
        }
        else if(str.compareTo("cancel") == 0){
            our_menu.getItem(0).setVisible(false);
            our_menu.getItem(1).setVisible(true);
            studentDetailsFragment = StudentDetailsFragment.newInstance(id);;////////////////////////////////////////////////////
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, studentDetailsFragment);
            fragmentTransaction.commit();
        }
    }*/

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        our_menu.getItem(0).setVisible(true);
        our_menu.getItem(1).setVisible(false);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, studentListFragment);
        fragmentTransaction.commit();
    }
}

