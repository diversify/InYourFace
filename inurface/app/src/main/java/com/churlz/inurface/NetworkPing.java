package com.churlz.inurface;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by CHURLZ on 14-11-22.
 */
public class NetworkPing extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... name) {
        BufferedReader in = null;
        String id = "";
        try{
            HttpClient httpclient = Data.httpclient;
            HttpGet request = new HttpGet();
            URI website = new URI("https://inyourface.herokuapp.com/songservice/ping");
            request.setURI(website);

            HttpResponse response = httpclient.execute(request);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line;
            String json = "";
            while ((line = in.readLine()) != null){
                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray suggestions = jsonObject.getJSONArray("suggestions");

            for(int i = 0; i < suggestions.length(); i++){
                JSONObject c = suggestions.getJSONObject(i);
                String uri = c.getString("songURI");
                String from = c.getString("from");
                String to = c.getString("to");
                id = c.getString("id");
                Data.receivedTrackId = uri;
                Data.receivedFrom = from;
                Log.d("PING", id);
            }
            try {
                JSONObject songs = jsonObject.getJSONObject("song");
                id = songs.getString("playSuggestionId");
            }catch(JSONException jse){
                Log.d("log_tag", "Error in http connection "+jse.toString());
            }
            return id;

        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            return "";
        }
    }
}