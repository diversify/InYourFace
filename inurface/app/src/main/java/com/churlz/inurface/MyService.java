package com.churlz.inurface;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CHURLZ on 14-11-22.
 */
public class MyService extends Service {
    IntentFilter filter = new IntentFilter(".MyBroadcastReceiver");
    private static Timer timer;

    final static String TAG = "SERVICE";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "My Service Created", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.queuechanged");
        timer = new Timer();
        registerReceiver(receiver, filter);
        startService();
    }

    void startService(){
        if(timer != null)
            timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            pingHandler.sendEmptyMessage(0);
        }
    }


    public void sendBroadcastMessage(String intentFilterName, int arg1, String extraKey) {
        Intent intent = new Intent(intentFilterName);
        if (arg1 != -1 && extraKey != null) {
            intent.putExtra(extraKey, arg1);
        }
        sendBroadcast(intent);
    }

    public final Handler pingHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            try{
                String result = new NetworkPing().execute("YUP").get();
                if(result.isEmpty() || result.equals("")){
                    ;
                }else{
                    sendBroadcastMessage("filter", Integer.parseInt(result), "id");
                }
            }catch (Exception e ){
                Log.d("PING BACK ALERT", "error in " + e.toString());
            }
        }
    };

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
        unregisterReceiver(receiver);
        timer.cancel();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart");
    }

    private final MyBroadcastReceiver receiver = new MyBroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
        }
    };
}
