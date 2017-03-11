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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements MediaController.MediaPlayerControl, SeekBar.OnSeekBarChangeListener {
    ArrayList<Itunes> itunesList;
    Context mContext;
    MediaPlayer mPlayer;
    boolean flagPlay = true;
    boolean flagPause = false;
    MediaController mController;
    ImageButton pauseButton;
    SeekBar seekBar;


    public ListAdapter(ArrayList<Itunes> itunesList, Context context, SeekBar seekBar, ImageButton pauseButton) {
        this.itunesList = itunesList;
        mContext = context;
        this.seekBar = seekBar;
        this.pauseButton = pauseButton;
    }

    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser)
            seekTo(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
                Log.d("demo","visible seekbar");
                seekBar.setVisibility(View.VISIBLE);
                int total;
                int currentPosition=0;

                if(flagPlay) {
                    pauseButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    mPlayer = new MediaPlayer();
                    //mController.show();
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
                    total = Integer.parseInt(itunesList.get(position).getDuration());
                    seekBar.setMax(total);
                    while(mPlayer!=null && currentPosition<total) {
                        currentPosition = getCurrentPosition()/1000;
                        seekBar.setProgress(currentPosition);
                        Log.d("demo",currentPosition+"");
                    }
                    flagPlay = false;
                }


            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagPause) {
                    pauseButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    pause();
                    flagPause = true;
                    flagPlay = true;
                } else{
                    pauseButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    start();
                    flagPause = false;
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
