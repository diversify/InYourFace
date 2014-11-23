package com.churlz.inurface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {

    final static String TAG = "MAIN ACTIVITY";
    Button buttonLogin, buttonCreate;
    EditText textField;
    IntentFilter filter = new IntentFilter("filter");
    boolean dataInitialized = false;
    String userName = "carl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!dataInitialized){
            Data.Data();
            dataInitialized = true;
        }

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonCreate = (Button) findViewById(R.id.buttonCreate);

        textField = (EditText) findViewById(R.id.editText);

        buttonLogin.setOnClickListener(this);
        buttonCreate.setOnClickListener(this);

        filter.addAction("filter_id");
        registerReceiver(receiver, filter);
        startService(new Intent(this, MyService.class));

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonLogin:
                login();
                break;
            case R.id.buttonCreate:
                createUser();
                break;
        }
    }

    void login(){
        userName = textField.getText().toString();
        new NetworkLogin().execute(userName);
        Intent friends = new Intent(this, FriendsActivity.class);
        startActivity(friends);
    }

    void sendSong(){
        new NetworkSend().execute(Data.trackId, userName, "carl");
    }

    void createUser(){
        new NetworkCreate().execute(textField.getText().toString());
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
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("SONG IN YOUR FACE!");
                alertDialog.setMessage("Do you want to share a magical moment with "+Data.receivedFrom+"?");
                alertDialog.setButton("YES!",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            new NetworkConfirm().execute(Integer.toString(id)).get();
                            String uri = Data.receivedTrackId;
                            Intent launcher = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(launcher);
                        }catch (Exception e) {
                            ;
                        }
                    }
                });
                alertDialog.setButton2("NO.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            new NetworkDeny().execute(Integer.toString(id)).get();
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