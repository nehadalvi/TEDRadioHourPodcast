package com.mad.tedradiohourpodcast;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Itunes itunes = (Itunes) getIntent().getExtras().getSerializable("tune");
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(itunes.getTitle());
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(itunes.getImgUrl()).into(iv);
        tv = (TextView) findViewById(R.id.tv_description);
        tv.setText("Description: "+ itunes.getDescription());
        tv = (TextView) findViewById(R.id.tv_date);
        tv.setText("Published Date:" +itunes.getDate());
        tv = (TextView) findViewById(R.id.tv_duration);
        tv.setText("Duration: " +itunes.getDuration());


        ImageButton playButton = (ImageButton) findViewById(R.id.pause_btn_playAct);
    }
}
