package com.mad.tedradiohourpodcast;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chinmay Rawool on 3/10/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<Itunes> itunesList;
    Context mContext;
    MediaPlayer mPlayer;
    boolean flagPlay = true;

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
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {
        TextView tv = (TextView) holder.view.findViewById(R.id.tv_title_grid);
        ImageView iv = (ImageView) holder.view.findViewById(R.id.iv_image_grid);
        tv.setText(itunesList.get(position).getTitle());
        Picasso.with(mContext).load(itunesList.get(position).getImgUrl()).into(iv);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "View clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,PlayActivity.class);
                intent.putExtra("tune",itunesList.get(position));
                mContext.startActivity(intent);
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagPlay) {
                    mPlayer = new MediaPlayer();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mPlayer.setDataSource(itunesList.get(position).getMp3Url());
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (SecurityException e) {
                        Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mPlayer.prepare();
                    } catch (IllegalStateException e) {
                        Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    }
                    mPlayer.start();
                    flagPlay = false;
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return itunesList.size();
    }
}
