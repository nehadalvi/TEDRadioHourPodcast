package com.mad.tedradiohourpodcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTunesAsync.IGetData{
    String url;
    RecyclerView listView;

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
        listView = (RecyclerView) findViewById(R.id.list_recycler_view);
        RecyclerView.Adapter adapter = new ListAdapter(itunes,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }
}
