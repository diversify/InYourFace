package com.churlz.inurface;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHURLZ on 14-11-22.
 */
public class NetworkSend extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... name) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = Data.httpclient;
        BufferedReader in;
        HttpPost httppost = new HttpPost("https://inyourface.herokuapp.com/playsuggestion");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("songURI", name[0]));
            nameValuePairs.add(new BasicNameValuePair("from", name[1]));
            nameValuePairs.add(new BasicNameValuePair("to", name[2]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            // NEW CODE
            String line;
            while ((line = in.readLine()) != null){
                Log.d("HTTP_SEND", line);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return null;
    }
}