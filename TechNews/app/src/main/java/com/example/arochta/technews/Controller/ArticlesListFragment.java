package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.R;

import com.example.arochta.technews.Model.Model;

import java.util.List;
/**
 * Created by arochta on 17/07/2017.
 */

public class ArticlesListFragment extends Fragment{

    ListView list;
    List<Article> data;
    int datasize = 0;
    ArticlesListAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public ArticlesListFragment() {
        // Required empty public constructor
    }

    public static ArticlesListFragment newInstance() {
        ArticlesListFragment fragment = new ArticlesListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        //TODO:get from DB
        data = Model.instace.getAllArticles();
        datasize = data.size();
        //data = swapData(Model.instace.getAllArticles());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_article_list, container, false);


        list = (ListView) contentView.findViewById(R.id.articlesList_list);

        adapter = new ArticlesListAdapter();

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int newPos = datasize - position-1;
                //Log.d("newpos", "row item was clicked at position: " + newPos);
                //adapter.notifyDataSetChanged();
                //Intent intent = new Intent(StudentsListActivity.this,StudentDetailsActivity.class);
                //intent.putExtra("StudentID",data.get(position).id);
                onButtonPressed(data.get(newPos).getArticleID());
                //Log.d("TAG","student id selected = " + data.get(position).id);
                //finish();
                //startActivityForResult(intent,REQUEST_ADD_ID);
                //finish();
            }
        });
        return contentView;
    }

    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteractionList(id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
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
        void onFragmentInteractionList(int id);
    }

    class ArticlesListAdapter extends BaseAdapter {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = inflater.inflate(R.layout.article_list_row,null);
            }

            TextView title = (TextView) convertView.findViewById(R.id.article_row_name);
            TextView author = (TextView) convertView.findViewById(R.id.article_row_author);
            ImageView imageview = (ImageView)convertView.findViewById(R.id.article_row_image);

            //write the newest article first
            int newPos = datasize - position-1;
            Article article = data.get(newPos);
            //Article article = data.get(position);
            title.setText(article.getTitle());
            author.setText(article.getAuthor().getName());
            //imageview.setImageDrawable(res);

            return convertView;
        }
    }

    /*public List<Article> swapData(List<Article> articles){
        int datasize = articles.size();
        for (int i = 0; i < (datasize/2); i++) {
            Article temp = articles.get(i);
            articles.set(i,articles.get(datasize-i-1));
            articles.set(datasize-i-1,temp);
        }
        return articles;
    }*/

}
