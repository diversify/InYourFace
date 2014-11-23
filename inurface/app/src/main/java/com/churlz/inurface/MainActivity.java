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
    Button buttonLogin;
    EditText textField;
    boolean dataInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!dataInitialized){
            Data.Data();
            dataInitialized = true;
        }

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textField = (EditText) findViewById(R.id.editText);

        buttonLogin.setOnClickListener(this);
        startService(new Intent(this, MyService.class));

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
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
        }
    }

    void login(){
        String name = textField.getText().toString();
        new NetworkCreate().execute(name);
        Data.userName = name;
        new NetworkLogin().execute(Data.userName);
        Intent friends = new Intent(this, FriendsActivity.class);
        startActivity(friends);
    }

}