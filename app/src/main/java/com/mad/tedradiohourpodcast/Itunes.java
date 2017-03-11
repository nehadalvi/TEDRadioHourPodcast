package com.mad.tedradiohourpodcast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by neha5 on 09-03-2017.
 */

public class Itunes implements Serializable {
    String title, description, pubDate, imgUrl, duration, mp3Url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getDate(){
        String str = getPubDate().substring(0,16);
        return str;
    }
    @Override
    public String toString() {
        return "Itunes{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", duration='" + duration + '\'' +
                ", mp3Url='" + mp3Url + '\'' +
                '}';
    }

    public static Comparator<Itunes> dateComparator = new Comparator<Itunes>() {
        @Override
        public int compare(Itunes o1, Itunes o2) {
            DateFormat df = new SimpleDateFormat("E, MMM dd yyyy");
            Date d1 = null, d2 = null;
            try {
                d1 = df.parse(o1.getDate());
                d2 = df.parse(o2.getDate());
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    };
}
