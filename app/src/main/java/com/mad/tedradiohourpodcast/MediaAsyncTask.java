package com.mad.tedradiohourpodcast;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Chinmay Rawool on 3/11/2017.
 */

public class MediaAsyncTask extends AsyncTask<String,Integer,Void> {
    SeekBar seekBar;
    ImageButton pause;
    int duration;
    boolean flag=true;
    int current=0;
    MediaPlayer mPlayer;


    public MediaAsyncTask(SeekBar sb, int duration, ImageButton pause) {
        this.seekBar = sb;
        this.duration = duration;
        this.pause =  pause;
        current=0;
        Log.d("demo","Inside Constructor, duration= "+duration+" cur"+current+"Seekbar id"+seekBar.getId());
    }
    @Override
    protected Void doInBackground(String... strings) {
        mPlayer = new MediaPlayer();
        Log.d("demo","media player created");
        //mController.show();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(strings[0]);
        } catch (IllegalArgumentException e) {
            //Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            //Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            //Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mPlayer.prepare();
        } catch (IllegalStateException e) {
            //Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            //Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }
        mPlayer.start();
        Log.d("demo","media player started");

        while(current<duration){
            publishProgress(current);
            current = mPlayer.getCurrentPosition()/1000;
            //Log.d("demo",current+"");
            //Log.d("demo","flagPlay"+ListAdapter.flagPlay);
            //Log.d("demo","Pressed"+ListAdapter.pressedNewPlay);
            if(MainActivity.flagPlay && MainActivity.pressedNewPlay){
                Log.d("demo","do exit"+"");
                //ListAdapter.flagPlay = false;
                return null;
            }

        }
        Log.d("demo","While done"+"");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("demo","OnPRe");
        MainActivity.pressedNewPlay=false;
        Log.d("demo","Pressed"+MainActivity.pressedNewPlay);
        seekBar.setVisibility(View.VISIBLE);
        pause.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("demo","OnPost");
        MainActivity.pressedNewPlay=false;
        Log.d("demo","Pressed"+MainActivity.pressedNewPlay);
        if(current==duration) {
            seekBar.setVisibility(View.INVISIBLE);
            pause.setVisibility(View.INVISIBLE);
        }
        mPlayer.pause();
        MainActivity.flagPlay = true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //Log.d("current", values[0]+"");
        seekBar.setProgress(values[0]);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICKED","CLICKED CLICKED CLICKED");
                if(flag) {
                    Log.d("demo","flag"+flag);
                    pause.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    mPlayer.pause();
                    flag = false;
                    MainActivity.flagPlay = true;
                    //ListAdapter.pressedNewPlay = true;
                } else{
                    Log.d("demo","flag"+flag);
                    pause.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    mPlayer.start();
                    flag =true;
                    MainActivity.flagPlay = false;
                }
            }
        });
    }


}
