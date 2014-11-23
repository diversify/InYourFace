package com.churlz.inurface;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHURLZ on 14-11-22.
 */
public class NetworkDeny extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... name) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = Data.httpclient;
        BufferedReader in = null;
        HttpPost httppost = new HttpPost("https://inyourface.herokuapp.com/songservice/deny");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", name[0]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            // NEW CODE
            String line;
            String json = "";
            while ((line = in.readLine()) != null){
                json += line;
            }

            JSONObject jsonObject = new JSONObject(json);
            String message = jsonObject.getString("message");
            if(message.equals("Song denied")){
                Data.receivedId="0";
                return true;
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (JSONException jsone){
            //
        }
        return null;
    }
}