package com.churlz.inurface;

import android.content.Intent;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

/**
 * Created by CHURLZ on 14-11-22.
 */
public class Data {
    public static String trackId;
    public static String artistName;
    public static String albumName;
    public static String trackName;
    public static boolean playing;
    public static int positionInMs;

    public static String userName = "carl";
    public static String sendingTo;

    public static String receivedTrackId;
    public static String receivedFrom;
    public static String receivedId;

    public static ArrayList<Friend> friends = new ArrayList<Friend>();

    public static HttpClient httpclient = new DefaultHttpClient();


    public static void Data(){
        trackId = "";
        artistName = "";
        albumName = "";
        trackName = "";
        playing = false;
        positionInMs = 0;

        receivedId = "0";
    }
}
