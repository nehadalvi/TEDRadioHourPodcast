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
 * Created by Chinmay Rawool on 3/10/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<Itunes> itunesList;
    Context mContext;

    public GridAdapter(ArrayList<Itunes> itunesList, Context context) {
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
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_layout, parent, false);


        ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        TextView tv = (TextView) holder.view.findViewById(R.id.tv_title_grid);
        ImageView iv = (ImageView) holder.view.findViewById(R.id.iv_image_grid);
        tv.setText(itunesList.get(position).getTitle());
        Picasso.with(mContext).load(itunesList.get(position).getImgUrl()).into(iv);
    }



    @Override
    public int getItemCount() {
        return itunesList.size();
    }
}
