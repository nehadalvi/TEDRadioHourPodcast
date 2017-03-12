package com.mad.tedradiohourpodcast;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
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

    boolean flagPause = false;
    ImageButton pauseButton;
    SeekBar seekBar;
    MediaAsyncTask mediaAsyncTask=null;

    public GridAdapter(ArrayList<Itunes> itunesList, Context context, SeekBar seekBar, ImageButton pauseButton) {
        this.itunesList = itunesList;
        mContext = context;
        this.seekBar = seekBar;
        this.pauseButton = pauseButton;
        MainActivity.flagPlay = true;
        MainActivity.pressedNewPlay = false;
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
        ImageButton btnPlay = (ImageButton) holder.view.findViewById(R.id.btn_play);
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

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display(position);
                MainActivity.pressedNewPlay = true;
                Log.d("demo","Pressed"+MainActivity.pressedNewPlay);
                Log.d("demo","visible seekbar");
                seekBar.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                int total;
                //int currentPosition=0;

                if(MainActivity.flagPlay) {

                    Log.d("demo","Pressed play button");
                    Log.d("demo","Duration:"+itunesList.get(position).getDuration());
                    total = Integer.parseInt(itunesList.get(position).getDuration());
                    pauseButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    seekBar.setMax(total);
                    mediaAsyncTask = (MediaAsyncTask) new MediaAsyncTask(seekBar,total,pauseButton);
                    Log.d("demo",itunesList.get(position).getMp3Url()+"");
                    mediaAsyncTask.execute(itunesList.get(position).getMp3Url());
                    Log.d("demo","position"+position+"");
                    MainActivity.flagPlay = false;
                }


            }
        });
    }



    @Override
    public int getItemCount() {
        return itunesList.size();
    }
}
