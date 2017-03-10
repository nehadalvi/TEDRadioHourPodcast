package com.mad.tedradiohourpodcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTunesAsync.IGetData{
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://www.npr.org/rss/podcast.php?id=510298";

        new GetTunesAsync(this).execute(url);

    }

    @Override
    public void getData(ArrayList<Itunes> itunes) {
        Log.d("demo","Arraylist = "+itunes.toString());


    }
}
