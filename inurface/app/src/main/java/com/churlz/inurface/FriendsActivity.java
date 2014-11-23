package com.churlz.inurface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by CHURLZ on 14-11-23.
 */
public class FriendsActivity extends Activity {
    ListView listView;
    FriendsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        try {
            ArrayList<Friend> list = new NetworkGetUsers().execute(" ").get();
            Data.friends = list;
        }catch (Exception e){
            ;
        }
        adapter = new FriendsAdapter(this, Data.friends);
        listView = (ListView)findViewById(R.id.listView);
        updateList();
    }

    void updateList(){
        listView.setAdapter(adapter);
    }
}
