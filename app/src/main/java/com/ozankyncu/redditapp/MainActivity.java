package com.ozankyncu.redditapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView myrecyclerview;
    private MyRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myrecyclerview=(RecyclerView)findViewById(R.id.myrecyclerview);
        final RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(layoutManager);
        adapter=new MyRecyclerAdapter(this,getData());
        myrecyclerview.setAdapter(adapter);
    }
    public List<ListItems> getData()
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
    }

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
