package com.mad.tedradiohourpodcast;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayActivity extends AppCompatActivity {
    int total;
    SeekBar seekBar;
    MediaAsyncTask mediaAsyncTask=null;
    public static boolean pressedBack = false;
    Date d=null;
    String formattedDate;

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if(mediaAsyncTask!=null){
            mediaAsyncTask.stop();
            //mediaAsyncTask.cancel(true);
            pressedBack = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.play_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar itunesToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(itunesToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ted);
        MainActivity.flagPlay = true;
        MainActivity.pressedNewPlay = false;

        final Itunes itunes = (Itunes) getIntent().getExtras().getSerializable("tune");
        DateFormat originalFormat = new SimpleDateFormat("E, dd MMM yyyy");
        DateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            d = originalFormat.parse(itunes.getDate());
            formattedDate = targetFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(itunes.getTitle());
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(itunes.getImgUrl()).into(iv);
        tv = (TextView) findViewById(R.id.tv_description);
        tv.setText("Description: "+ itunes.getDescription());
        tv = (TextView) findViewById(R.id.tv_date);
        if(d!=null) {
            tv.setText("Publication Date: " +formattedDate);
        }
        tv = (TextView) findViewById(R.id.tv_duration);
        tv.setText("Duration: " +itunes.getDuration());

        seekBar = (SeekBar) findViewById(R.id.seekBar_playAct);
        final ImageButton imageButton = (ImageButton) findViewById(R.id.pause_btn_playAct);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.flagPlay){
                    pressedBack = false;
                    Log.d("demo","flagPlay ="+MainActivity.flagPlay);
                    imageButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    total = Integer.parseInt(itunes.getDuration());
                    seekBar.setMax(total);
                    mediaAsyncTask = (MediaAsyncTask) new MediaAsyncTask(seekBar,total,imageButton);
                    mediaAsyncTask.execute(itunes.getMp3Url());
                    //Log.d("demo","position"+position+"");
                    MainActivity.flagPlay = false;
                }
            }
        });
    }
}
