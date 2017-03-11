package com.mad.tedradiohourpodcast;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chinmay Rawool on 3/10/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements MediaController.MediaPlayerControl{
    ArrayList<Itunes> itunesList;
    Context mContext;
    MediaPlayer mPlayer;
    boolean flagPlay = true;
    boolean flagPause = false;
    MediaController mController;
    ImageButton pauseButton;

    public GridAdapter(ArrayList<Itunes> itunesList, Context context, MediaController mediaController, ImageButton pauseButton) {
        this.itunesList = itunesList;
        mContext = context;
        mController = mediaController;
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
                if(flagPlay) {
                    pauseButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    mPlayer = new MediaPlayer();
                    mController.show();
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
}
