package com.ozankyncu.redditapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by ozankoyuncu on 27.3.2016.
 */
public class ListRowViewHolder extends RecyclerView.ViewHolder {
    protected NetworkImageView thumbnail;
    protected TextView title,author,url,subreddit;
    protected RelativeLayout relativeLayout;
    public ListRowViewHolder(View itemView) {
        super(itemView);
        this.title=(TextView)itemView.findViewById(R.id.title);
        this.author=(TextView)itemView.findViewById(R.id.author);
        this.url=(TextView)itemView.findViewById(R.id.url);
        this.subreddit=(TextView)itemView.findViewById(R.id.subreddit);
        this.relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relLayout);
        this.thumbnail=(NetworkImageView)itemView.findViewById(R.id.network_image);
        itemView.setClickable(true);
    }
}
