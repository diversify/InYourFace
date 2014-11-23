package com.churlz.inurface;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by CHURLZ on 14-11-22.
 */
public class NetworkGetUsers extends AsyncTask<String, Void, ArrayList<Friend>> {

    @Override
    protected ArrayList<Friend> doInBackground(String... name) {
        BufferedReader in = null;
        String id = "";
        ArrayList<Friend> friends = new ArrayList<Friend>();
        try{
            HttpClient httpclient = Data.httpclient;
            HttpGet request = new HttpGet();
            URI website = new URI("https://inyourface.herokuapp.com/user");
            request.setURI(website);

            HttpResponse response = httpclient.execute(request);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line;
            String json = "";
            while ((line = in.readLine()) != null){
                json += line;
            }
            JSONArray users = new JSONArray(json);

            for(int i = 0; i < users.length(); i++){
                JSONObject c = users.getJSONObject(i);
                String user = c.getString("username");
                Log.d("USER", user);
                if(!user.equals(Data.userName))
                    friends.add(new Friend(user));
            }


            return friends;

        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            return null;
        }
    }
}