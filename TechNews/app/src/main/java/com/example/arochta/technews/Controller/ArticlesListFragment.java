package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.example.arochta.technews.Model.Article;
import com.example.arochta.technews.Model.ArticleFirebase;
import com.example.arochta.technews.R;

import com.example.arochta.technews.Model.Model;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Model.UpdateArticleEvent event) {
        Toast.makeText(getActivity(), "got new article event", Toast.LENGTH_SHORT).show();
        boolean exist = false;
        for (Article article: data){
            if (article.getArticleID() == (event.article.getArticleID())){
                article = event.article;
                exist = true;
                break;
            }
        }
        if (!exist){
            Log.d("list", event.article.toString());
            data.add(event.article);
        }
        Model.instace.getAllArticles(new ArticleFirebase.GetAllArticlesAndObserveCallback() {
            @Override
            public void onComplete(List<Article> list) {
                data.clear();
                data = list;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });
        adapter.notifyDataSetChanged();
        list.setSelection(adapter.getCount() - 1);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        data = new LinkedList<Article>();
        /*//TODO:get from DB

        Model.instace.getAllArticles(new ArticleFirebase.GetAllArticlesAndObserveCallback() {
            @Override
            public void onComplete(List<Article> list) {
                data = list;
            }

            @Override
            public void onCancel() {
                data = null;
            }
        });*/
        //datasize = data.size();
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

        Model.instace.getAllArticles(new ArticleFirebase.GetAllArticlesAndObserveCallback() {
            @Override
            public void onComplete(List<Article> list) {
                data.clear();
                data = list;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });

        EventBus.getDefault().register(this);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            final ImageView imageview = (ImageView)convertView.findViewById(R.id.article_row_image);

            datasize = data.size();
            if(datasize != 0) {
                int newPos = datasize - position - 1;
                Article article = data.get(newPos);
                Log.d("newlist", article.toString());
                title.setText(article.getTitle());
                author.setText(article.getAuthor());
                Model.instace.getImage(article.getImg(), new Model.GetImageListener() {
                    @Override
                    public void onSuccess(Bitmap image) {
                        imageview.setImageBitmap(image);
                    }

                    @Override
                    public void onFail() {
                        Log.d("listImg", "fail");
                    }
                });
            }
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
