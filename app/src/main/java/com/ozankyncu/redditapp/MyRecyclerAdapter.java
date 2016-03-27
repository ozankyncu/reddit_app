package com.ozankyncu.redditapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by ozankoyuncu on 27.3.2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolder> {
    private Context mContext;
    private List<ListItems> listItemsList;
    private ImageLoader mImageLoader;
    private int focusedItem=0;

    public MyRecyclerAdapter(Context mContext,List<ListItems> listItemsList) {
        this.mContext = mContext;
        this.listItemsList=listItemsList;
    }

    @Override
    public ListRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_row,parent,false);
        ListRowViewHolder listRowViewHolder=new ListRowViewHolder(view);
        return listRowViewHolder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolder holder, int position) {
            ListItems listItems=listItemsList.get(position);
            holder.itemView.setSelected(focusedItem==position);
            holder.title.setText(listItems.getTitle());
            holder.author.setText(listItems.getAuthor());
            holder.getLayoutPosition();

    }
    public  void clearAdapter()
    {
        listItemsList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount()  {
        return (null!= listItemsList ? listItemsList.size() : 0);
    }
}
