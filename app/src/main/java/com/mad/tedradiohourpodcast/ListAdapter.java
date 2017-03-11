package com.mad.tedradiohourpodcast;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by neha5 on 09-03-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<Itunes> itunesList;
    Context mContext;
    MediaPlayer mPlayer;
    LinearLayout linear;
    boolean flagPlay = true;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView tv = (TextView) holder.view.findViewById(R.id.tv_title_date);
        ImageView iv = (ImageView) holder.view.findViewById(R.id.iv_image);
        tv.setText(itunesList.get(position).getTitle()+"\n"+"Posted: "+itunesList.get(position).getDate());
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


        holder.view.findViewById(R.id.iv_play_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display(position);
                Toast.makeText(mContext, "Play audio clicked :"+position, Toast.LENGTH_SHORT).show();
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
