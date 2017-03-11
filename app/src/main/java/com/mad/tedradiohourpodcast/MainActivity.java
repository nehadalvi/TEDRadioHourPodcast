package com.mad.tedradiohourpodcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTunesAsync.IGetData{
    String url;
    RecyclerView listView;
    boolean flagList = true;
    ArrayList<Itunes> tunesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar itunesToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(itunesToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ted);
        url = "https://www.npr.org/rss/podcast.php?id=510298";

        new GetTunesAsync(this).execute(url);

    }

    @Override
    public void getData(ArrayList<Itunes> itunes) {
        Log.d("demo","Arraylist = "+itunes.toString());
        tunesList = itunes;
        listView = (RecyclerView) findViewById(R.id.list_recycler_view);
        RecyclerView.Adapter adapter = new ListAdapter(tunesList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_switch){
            if(flagList){
                listView = (RecyclerView) findViewById(R.id.list_recycler_view);
                RecyclerView.Adapter adapter = new GridAdapter(tunesList,this);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
                listView.setLayoutManager(layoutManager);
                listView.setAdapter(adapter);
                flagList = false;
            } else{
                listView = (RecyclerView) findViewById(R.id.list_recycler_view);
                RecyclerView.Adapter adapter = new ListAdapter(tunesList,this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                listView.setLayoutManager(layoutManager);
                listView.setAdapter(adapter);
                flagList = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
