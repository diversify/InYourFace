package com.churlz.inurface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by CHURLZ on 14-11-23.
 */
public class FriendsActivity extends Activity {
    ListView listView;
    FriendsAdapter adapter;
    Context context;
    IntentFilter filter = new IntentFilter("filter");

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

        filter.addAction("filter_id");
        registerReceiver(receiver, filter);

        context = this;
        adapter = new FriendsAdapter(this, Data.friends);
        listView = (ListView)findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend f = (Friend)adapterView.getItemAtPosition(i);
                Data.sendingTo = f.name;
                Intent face = new Intent(context, SendInFace.class);
                startActivity(face);
            }
        });
        updateList();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    void updateList(){
        listView.setAdapter(adapter);
    }

    private final MyBroadcastReceiver receiver = new MyBroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("filter")){
                final int id = intent.getIntExtra("id", 0);
                if(id == Integer.parseInt(Data.receivedId)){
                    return;
                }
                if(id == Integer.parseInt(Data.dbId)){
                    String uri = Data.trackId;
                    Intent launcher = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(launcher);
                }

                AlertDialog alertDialog = new AlertDialog.Builder(FriendsActivity.this).create();
                alertDialog.setTitle("SONG IN YOUR FACE!");
                alertDialog.setMessage("Do you want to share a magical moment with "+Data.receivedFrom+"?");
                alertDialog.setButton("YES!",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            new NetworkConfirm().execute(Integer.toString(id)).get();
                            String uri = Data.receivedTrackId;
                            Intent launcher = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(launcher);
                            dialog.dismiss();
                        }catch (Exception e) {
                            ;
                        }
                    }
                });
                alertDialog.setButton2("NO.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            new NetworkDeny().execute(Integer.toString(id)).get();
                            dialog.dismiss();
                        }catch (Exception e) {
                            ;
                        }
                    }
                });
                alertDialog.show();
                Data.receivedId = Integer.toString(id);
            }
        }
    };
}
