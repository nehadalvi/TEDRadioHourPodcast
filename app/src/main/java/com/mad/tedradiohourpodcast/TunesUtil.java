package com.mad.tedradiohourpodcast;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by neha5 on 23-02-2017.
 */

public class TunesUtil {
    static class TunesPullParser{

        static ArrayList<Itunes> parseTunes(InputStream in) throws XmlPullParserException, IOException {
            ArrayList<Itunes> tuneList = new ArrayList<Itunes>();
            Itunes tune = null;
            boolean itemFlag = false;
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");

            int event = parser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("item")){
                            itemFlag = true;
                            tune = new Itunes();
                        } else if(parser.getName().equals("title") && itemFlag){
                            tune.setTitle(parser.nextText().trim());
                        } else if(parser.getName().equals("description") && itemFlag){
                            tune.setDescription(parser.nextText().trim());
                        } else if(parser.getName().equals("pubDate") && itemFlag){
                            tune.setPubDate(parser.nextText().trim());
                        } else if(parser.getName().equals("itunes:image") && itemFlag){
                            tune.setImgUrl(parser.getAttributeValue(null,"href"));
                        } else if(parser.getName().equals("enclosure") && itemFlag){
                            tune.setMp3Url(parser.getAttributeValue(null,"url"));
                        } else if(parser.getName().equals("itunes:duration") && itemFlag){
                            tune.setDuration(parser.nextText().trim());
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            itemFlag = false;
                            tuneList.add(tune);
                            tune=null;
                        }
                        break;
                    default: break;
                }

                event = parser.next();
            }

//            Log.d("demo","Tune = "+tune.toString());

            return tuneList;
        }
    }
}

