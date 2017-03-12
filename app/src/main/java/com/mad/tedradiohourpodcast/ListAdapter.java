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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
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
    public static boolean flagPlay = true;
    public static boolean pressedNewPlay = false;
    boolean flagPause = false;
    ImageButton pauseButton;
    SeekBar seekBar;
    MediaAsyncTask mediaAsyncTask=null;

    public ListAdapter(ArrayList<Itunes> itunesList, Context context, SeekBar seekBar, ImageButton pauseButton) {
        this.itunesList = itunesList;
        mContext = context;
        this.seekBar = seekBar;
        this.pauseButton = pauseButton;
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
                pressedNewPlay = true;
                Log.d("demo","Pressed"+pressedNewPlay);
                Log.d("demo","visible seekbar");
                seekBar.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                int total;
                //int currentPosition=0;

                if(flagPlay) {

                    Log.d("demo","Pressed play button");
                    Log.d("demo","Duration:"+itunesList.get(position).getDuration());
                    total = Integer.parseInt(itunesList.get(position).getDuration());
                    pauseButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    seekBar.setMax(total);
                    mediaAsyncTask = (MediaAsyncTask) new MediaAsyncTask(seekBar,total,pauseButton);
                    Log.d("demo",itunesList.get(position).getMp3Url()+"");
                    mediaAsyncTask.execute(itunesList.get(position).getMp3Url());
                    Log.d("demo","position"+position+"");
                    flagPlay = false;
                }


            }
        });



    }



    @Override
    public int getItemCount() {
        return itunesList.size();
    }
    void display(int i){
        Toast.makeText(mContext,"Play button clicked : "+i, Toast.LENGTH_SHORT).show();
    }
}
