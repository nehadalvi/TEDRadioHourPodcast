package com.mad.tedradiohourpodcast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by neha5 on 09-03-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<Itunes> itunesList;
    Context mContext;

    public ListAdapter(ArrayList<Itunes> itunesList, Context context) {
        this.itunesList = itunesList;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView tv = (TextView) holder.view.findViewById(R.id.tv_title_date);
        ImageView iv = (ImageView) holder.view.findViewById(R.id.iv_image);
        tv.setText(itunesList.get(position).getTitle()+"\n"+itunesList.get(position).getDescription());
        Picasso.with(mContext).load(itunesList.get(position).getImgUrl()).into(iv);
    }



    @Override
    public int getItemCount() {
        return itunesList.size();
    }
}
