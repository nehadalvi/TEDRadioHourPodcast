package com.mad.tedradiohourpodcast;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by neha5 on 23-02-2017.
 */

public class GetTunesAsync extends AsyncTask<String, Void, ArrayList<Itunes>> {
    IGetData activity;

    public GetTunesAsync(IGetData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Itunes> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();

                return TunesUtil.TunesPullParser.parseTunes(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Itunes> itunes) {
        super.onPostExecute(itunes);
        activity.getData(itunes);
    }

    public static interface IGetData{
        public void getData(ArrayList<Itunes> itunes);
    }
}
