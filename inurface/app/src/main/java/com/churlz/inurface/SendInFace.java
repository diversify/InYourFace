package com.churlz.inurface;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by CHURLZ on 14-11-23.
 */
public class SendInFace extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        new NetworkSend().execute(Data.trackId, Data.userName, Data.sendingTo);
    }
}