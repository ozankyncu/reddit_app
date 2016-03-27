package com.ozankyncu.redditapp;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView myrecyclerview;
    private List<ListItems> listItemsList=new ArrayList<ListItems>();
    private MyRecyclerAdapter adapter;
    private String count;
    private String after_id;
    private String jsonSubreddit;
    public  static  final String TAG="MyRecyclerList";
    private int counter=0;
    private static final String aww="aww";
    private static final String subredditUrl ="http://www.reddit.com/r/";
    private static final String jsonEnd = "/.json";
    private static final String qCount = "?count=";
    private static final String after = "&after=";
    private ProgressDialog progressDialog;
    com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myrecyclerview=(RecyclerView)findViewById(R.id.myrecyclerview);
        final RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(layoutManager);
       /* myrecyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.BLUE)
                        .build());*/
        //adapter=new MyRecyclerAdapter(this,getData());
        //myrecyclerview.setAdapter(adapter);

       /* myrecyclerview.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition=((LinearLayoutManager)myrecyclerview.getLayoutManager()).findFirstVisibleItemPosition();
                ((LinearLayoutManager)myrecyclerview.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                loadMore(jsonSubreddit);
            }
        });*/
        refreshLayout= (com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                loadMore(jsonSubreddit);
                refreshLayout.setRefreshing(false);
            }
        });

        updateList(aww);

    }


    private void updateList(String subredit) {
        counter=0;
        subredit=subredditUrl+subredit+jsonEnd;
        adapter=new MyRecyclerAdapter(this,listItemsList);
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.setAdapter(new AlphaInAnimationAdapter(adapter));
        RequestQueue queue= Volley.newRequestQueue(this);
        adapter.clearAdapter();
        showPD();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, subredit, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG,response.toString());
                hidePD();
                try{
                    JSONObject data= response.getJSONObject("data");
                    after_id=data.getString("after");
                    JSONArray children=data.getJSONArray("children");
                    for (int i = 0; i <children.length() ; i++) {
                        JSONObject post=children.getJSONObject(i).getJSONObject("data");
                        ListItems item=new ListItems();
                        item.setTitle(post.getString("title"));
                        item.setThumbnail(post.getString("thumbnail"));
                        item.setUrl(post.getString("url"));
                        item.setSubreddit(post.getString("subreddit"));
                        item.setAuthor(post.getString("author"));
                        jsonSubreddit=post.getString("subreddit");
                        listItemsList.add(item);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.e(TAG,volleyError.getMessage());
                hidePD();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void loadMore(String subredit) {
        counter=counter+25;
        count=String.valueOf(counter);
        subredit=jsonSubreddit;
        subredit=subredditUrl+subredit+jsonEnd+qCount+after+after_id;

        adapter=new MyRecyclerAdapter(this,listItemsList);
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.setAdapter(new AlphaInAnimationAdapter(adapter));
        showPD();
        RequestQueue queue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, subredit, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG,response.toString());
                hidePD();
                try{
                    JSONObject data= response.getJSONObject("data");
                    after_id=data.getString("after");
                    JSONArray children=data.getJSONArray("children");
                    for (int i = 0; i <children.length() ; i++) {
                        JSONObject post=children.getJSONObject(i).getJSONObject("data");
                        ListItems item=new ListItems();
                        item.setTitle(post.getString("title"));
                        item.setThumbnail(post.getString("thumbnail"));
                        item.setUrl(post.getString("url"));
                        item.setSubreddit(post.getString("subreddit"));
                        item.setAuthor(post.getString("author"));
                        jsonSubreddit=post.getString("subreddit");
                        listItemsList.add(item);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.e(TAG,volleyError.getMessage());
                hidePD();
            }
        });
        queue.add(jsonObjectRequest);
    }
    private void showPD() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    private void hidePD() {
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }




    //TODO: DENEME AMACLI OLUSTURULMUSTUR.
    /*public List<ListItems> getData()
    {
        List<ListItems> list=new ArrayList<ListItems>();
        String[] titles={"BLA","BLA","BLA","BLA","BLA","BLA","BLA","BLA","BLA","BLA","BLA","BLA"};
        String[] Authors={"BLAs","BLAs","BLAs","BLA","BLA","BLA","BLA","BLA","BLA","BLA","BLA","BLA"};
        for (int i = 0; i <titles.length ; i++) {
            ListItems listItems=new ListItems();
            listItems.setTitle(titles[0]);
            listItems.setAuthor(Authors[0]);
            list.add(listItems);
        }
        return list;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
